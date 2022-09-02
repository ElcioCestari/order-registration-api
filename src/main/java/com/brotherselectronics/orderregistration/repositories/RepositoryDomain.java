package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

@Getter
@Deprecated(forRemoval = true)
/**
 * This was deprecated but was keeped to see the code
 */
public enum RepositoryDomain {
    PRODUCT(Product.class),
    ORDER(Order.class);

    private final Class<?> clazz;
    private BaseRepository baseRepository;

    RepositoryDomain(Class<? extends BaseEntityImp> entity) {
        clazz = entity;
    }

    @Component
    public static class RepositoryDomainInjector {

        @Autowired
        private ProductRepository productRepository;
        @Autowired
        private OrderRepository orderRepository;

        @PostConstruct
        public void postConstruct() {
            for (RepositoryDomain domain : EnumSet.allOf(RepositoryDomain.class))
                switch (domain) {
                    case ORDER -> domain.setBaseRepository(orderRepository);
                    case PRODUCT -> domain.setBaseRepository(productRepository);
                    default ->
                            throw new IllegalArgumentException("RepositoryDomain [" + domain.name() + "] not implemented yet");
                }
        }
    }

    private void setBaseRepository(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }
}
