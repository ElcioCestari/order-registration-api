package com.brotherselectronics.orderregistration.domains.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GrantedAuthorityTest {

    @Test
    void getRole() {
        String adminRole = GrantedAuthority.ADMIN.getAuthority().getAuthority();
        String userRole = GrantedAuthority.USER.getAuthority().getAuthority();

        Assertions.assertEquals(adminRole, "ROLE_ADMIN");
        Assertions.assertEquals(userRole, "ROLE_USER");
    }

    @Test
    void values() {
        GrantedAuthority[] authorities = GrantedAuthority.values();
        Assertions.assertEquals(authorities.length, 2);
    }

    @Test
    void valueOf() {
        GrantedAuthority admin = GrantedAuthority.valueOf("ADMIN");
        Assertions.assertNotNull(admin);
        GrantedAuthority user = GrantedAuthority.valueOf("USER");
        Assertions.assertNotNull(user);
    }
}