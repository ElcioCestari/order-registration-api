package com.brotherselectronics.orderregistration.configs;

import com.brotherselectronics.orderregistration.services.CategoryService;
import com.brotherselectronics.orderregistration.services.OrderService;
import com.brotherselectronics.orderregistration.services.ProductService;
import com.brotherselectronics.orderregistration.services.SystemUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

/**
 * Authorization matrix: who may call each endpoint. Services are mocked because only the security
 * decision matters here. "allowed" means the request got past security, whatever the controller
 * answered next (200, 400, 404...); a denied request gets 401 (not logged in) or 403 (wrong profile).
 */
@SpringBootTest
class AuthorizationRulesTest {

    private static final String ANONYMOUS = "ANONYMOUS";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private OrderService orderService;
    @MockitoBean
    private SystemUserService systemUserService;
    @MockitoBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @ParameterizedTest(name = "{0} {1} as {2} -> {3}")
    @CsvSource(textBlock = """
            # method, path,                  profile,   expected
            # Identity
            POST,   /users/login,            ADMIN,     allowed
            POST,   /users/login,            CUSTOMER,  allowed
            POST,   /users/login,            ANONYMOUS, 401
            GET,    /users,                  ADMIN,     allowed
            GET,    /users,                  CUSTOMER,  403
            GET,    /users,                  ANONYMOUS, 401
            GET,    /users/some-id,          ADMIN,     allowed
            GET,    /users/some-id,          CUSTOMER,  403
            POST,   /users,                  ADMIN,     allowed
            POST,   /users,                  CUSTOMER,  403
            POST,   /users,                  ANONYMOUS, 401
            PUT,    /users/some-id,          ADMIN,     allowed
            PUT,    /users/some-id,          CUSTOMER,  403
            DELETE, /users/some-id,          ADMIN,     allowed
            DELETE, /users/some-id,          CUSTOMER,  403
            # Catalog
            GET,    /products,               ADMIN,     allowed
            GET,    /products,               CUSTOMER,  allowed
            GET,    /products,               ANONYMOUS, 401
            GET,    /products/all,           CUSTOMER,  allowed
            GET,    /products/some-id,       CUSTOMER,  allowed
            POST,   /products,               ADMIN,     allowed
            POST,   /products,               CUSTOMER,  403
            PUT,    /products/some-id,       ADMIN,     allowed
            PUT,    /products/some-id,       CUSTOMER,  403
            PATCH,  /products/some-id,       ADMIN,     allowed
            PATCH,  /products/some-id,       CUSTOMER,  403
            DELETE, /products/some-id,       ADMIN,     allowed
            DELETE, /products/some-id,       CUSTOMER,  403
            GET,    /categories,             CUSTOMER,  allowed
            GET,    /categories/ELETRONIC,   CUSTOMER,  allowed
            GET,    /categories,             ANONYMOUS, 401
            POST,   /categories,             ADMIN,     403
            # Ordering (ADMIN only for now)
            GET,    /orders,                 ADMIN,     allowed
            GET,    /orders,                 CUSTOMER,  403
            GET,    /orders,                 ANONYMOUS, 401
            GET,    /orders/some-id,         ADMIN,     allowed
            GET,    /orders/some-id,         CUSTOMER,  403
            POST,   /orders,                 ADMIN,     allowed
            POST,   /orders,                 CUSTOMER,  403
            PUT,    /orders/some-id,         ADMIN,     allowed
            PUT,    /orders/some-id,         CUSTOMER,  403
            DELETE, /orders/some-id,         ADMIN,     allowed
            DELETE, /orders/some-id,         CUSTOMER,  403
            # Documentation
            GET,    /v3/api-docs,            CUSTOMER,  allowed
            GET,    /v3/api-docs,            ANONYMOUS, 401
            # Not listed: denied by default
            GET,    /not-mapped,             ADMIN,     403
            GET,    /not-mapped,             ANONYMOUS, 401
            """)
    void authorizationRules(String method, String path, String profile, String expected) throws Exception {
        int status = mockMvc.perform(requestAs(method, path, profile)).andReturn().getResponse().getStatus();

        if ("allowed".equals(expected)) {
            assertThat(status).as("status of %s %s as %s", method, path, profile).isNotIn(401, 403);
        } else {
            assertThat(status).as("status of %s %s as %s", method, path, profile)
                    .isEqualTo(Integer.parseInt(expected));
        }
    }

    private static MockHttpServletRequestBuilder requestAs(String method, String path, String profile) {
        MockHttpServletRequestBuilder builder = request(HttpMethod.valueOf(method), path)
                .contentType(APPLICATION_JSON)
                .content("{}");
        if (ANONYMOUS.equals(profile)) {
            return builder.with(anonymous());
        }
        return builder.with(user("someone").authorities(new SimpleGrantedAuthority(profile)));
    }
}
