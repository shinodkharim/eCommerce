package com.curiez.admin.service.mapper;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper extends DomainMapper<Product,ProductDTO> {

}
