package com.brotherselectronics.orderregistration.domains.mappers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.mapstruct.*;

import static org.mapstruct.NullValueCheckStrategy.*;
import static org.mapstruct.NullValuePropertyMappingStrategy.*;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductRequestDTO, ProductResponseDTO> {

    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
    void merge(ProductRequestDTO source, @MappingTarget Product target);
}