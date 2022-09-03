package com.brotherselectronics.orderregistration.domains.enums;

import org.junit.jupiter.api.Test;

import static com.brotherselectronics.orderregistration.domains.enums.GrantedAuthority.ADMIN;
import static com.brotherselectronics.orderregistration.domains.enums.GrantedAuthority.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GrantedAuthorityTest {

    @Test
    void getRole() {
        String adminRole = ADMIN.getAuthority().getAuthority();
        String userRole = USER.getAuthority().getAuthority();

        assertEquals(adminRole, Role.ADMIN.getAuthority());
        assertEquals(userRole, Role.USER.getAuthority());
    }

    @Test
    void values() {
        GrantedAuthority[] authorities = GrantedAuthority.values();
        assertEquals(2, authorities.length);
    }

    @Test
    void valueOf() {
        GrantedAuthority admin = GrantedAuthority.valueOf("ADMIN");
        assertNotNull(admin);
        GrantedAuthority user = GrantedAuthority.valueOf("USER");
        assertNotNull(user);
    }
}