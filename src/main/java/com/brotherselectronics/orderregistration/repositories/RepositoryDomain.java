package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * This enum does switch between repositories.
 *
 * @deprecated - This was deprecated but was kept to see the code.
 */
@Getter
@Deprecated(forRemoval = true)
public enum RepositoryDomain {
    PRODUCT(Product.class),
    ORDER(Order.class);

    private final Class<?> clazz;
    private BaseRepository baseRepository;

    RepositoryDomain(Class<? extends BaseEntityImp> entity) {
        clazz = entity;
    }

    @Component
    @AllArgsConstructor
    public static class RepositoryDomainInjector {

        private final ProductRepository productRepository;
        private final OrderRepository orderRepository;

        @PostConstruct
        public void postConstruct() {
            for (RepositoryDomain domain : EnumSet.allOf(RepositoryDomain.class)) {
                switch (domain) {
                    case ORDER -> domain.setBaseRepository(orderRepository);
                    case PRODUCT -> domain.setBaseRepository(productRepository);
                    default -> throw new IllegalArgumentException(
                            "RepositoryDomain [" + domain.name() + "] not implemented yet");
                }
            }
        }
    }

    private void setBaseRepository(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
}
