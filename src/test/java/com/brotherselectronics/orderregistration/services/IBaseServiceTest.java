package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.brotherselectronics.orderregistration.domains.entities.SystemUser.Factory.buildAdmin;
import static com.brotherselectronics.orderregistration.domains.entities.SystemUser.Factory.simpleUser;
import static com.brotherselectronics.orderregistration.domains.enums.Role.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class IBaseServiceTest {
    @InjectMocks
    private SystemUserService userService;
    @InjectMocks
    private OrderService orderService;

    @InjectMocks
    private ProductService productService;
    private BigDecimal totalValueExpected;
    private String testId;

    @BeforeEach
    void setUp() {
        totalValueExpected = BigDecimal.valueOf(155.75);
        testId = "id teste";
    }

    @Test
    void merge() {

        Order target = Order.builder()
                .payment(false)
                .build();

        Order source = Order.builder()
                .payment(true)
                .orderItens(List.of(new OrderItem()))
                .totalValueOrder(totalValueExpected)
                .build();

        orderService.merge(source, target);

        assertThat(target.getTotalValueOrder()).isEqualTo(totalValueExpected);
        assertTrue(target.isPayment());
        assertThat(target.getOrderItens()).isNotEmpty();
    }

    @Test
    void merge_whenHasId_assertThatItsNotMerged() {

        Order target = Order.builder()
                .payment(false)
                .build();
        target.setId("outro id");

        Order source = Order.builder()
                .payment(true)
                .orderItens(List.of(new OrderItem()))
                .totalValueOrder(totalValueExpected)
                .build();
        source.setId(testId);

        orderService.merge(source, target);

        assertThat(target.getId()).isNotEqualTo(testId);
    }

    @Test
    void testMerge_whenSourceIsNullAndTargetNot_thenAssertThatTargetFieldWillBeNullToo() {
        Product target = Product.builder()
                .description("some name")
                .haveInStock(true)
                .unitPurchasePrice(BigDecimal.valueOf(1000.00))
                .unitPurchaseSale(BigDecimal.valueOf(1500.00))
                .build();
        Product source = Product.builder()
                .description(null)
                .haveInStock(false)
                .unitPurchasePrice(null)
                .unitPurchaseSale(null)
                .build();

        productService.merge(source, target);
        assertThat(target.getDescription()).isNull();
    }

    @Test
    void testMerge_givenASourceWithCollections_thenAssertThatTargetWillBeSameCollections() {
        var target = simpleUser("elcio", "elcio");
        var source = buildAdmin("elcio", "elcio_admin");

        assertThat(target.getAuthorities().stream().findFirst().get().toString()).isEqualTo(USER.getAuthority());
        assertThat(source.getAuthorities().stream().findFirst().get().toString()).isEqualTo(ADMIN.getAuthority());

        userService.merge(source, target);
        assertThat(target.getAuthorities()).isEqualTo(source.getAuthorities());
        assertThat(target.getAuthorities().stream().findFirst().get().toString()).isEqualTo(ADMIN.getAuthority());
    }
}