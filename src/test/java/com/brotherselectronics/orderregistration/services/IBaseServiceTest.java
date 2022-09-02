package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class IBaseServiceTest {
    @InjectMocks
    private OrderService orderService;
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
}