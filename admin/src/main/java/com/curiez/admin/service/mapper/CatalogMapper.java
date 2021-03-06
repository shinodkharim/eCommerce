package com.curiez.admin.service.mapper;

import com.curiez.admin.dto.CatalogDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.model.Catalog;
import com.curiez.admin.model.Sku;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CatalogMapper extends DomainMapper<Catalog, CatalogDTO> {
}
