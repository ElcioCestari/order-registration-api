package com.brotherselectronics.orderregistration.domains.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GrantedAuthorityTest {

    @Test
    void getRole() {
        String adminRole = GrantedAuthority.ADMIN.getAuthority().getAuthority();
        String userRole = GrantedAuthority.USER.getAuthority().getAuthority();

        Assertions.assertEquals(adminRole, Role.ADMIN.getAuthority());
        Assertions.assertEquals(userRole, Role.USER.getAuthority());
    }

    @Test
    void values() {
        GrantedAuthority[] authorities = GrantedAuthority.values();
        Assertions.assertEquals(2, authorities.length);
    }

    @Test
    void valueOf() {
        GrantedAuthority admin = GrantedAuthority.valueOf("ADMIN");
        Assertions.assertNotNull(admin);
        GrantedAuthority user = GrantedAuthority.valueOf("USER");
        Assertions.assertNotNull(user);
    }
}