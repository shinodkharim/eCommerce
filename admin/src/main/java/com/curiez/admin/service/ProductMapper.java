package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE
        ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDTO toDTO(Product productEntity);
    Product  toEntity(ProductDTO productDTO);
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product productEntity);
    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

}
