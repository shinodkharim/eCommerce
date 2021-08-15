package com.curiez.admin.service;

import com.curiez.admin.dto.CatalogDTO;
import com.curiez.admin.model.Catalog;
import com.curiez.admin.repository.CatalogRepository;
import com.curiez.admin.service.mapper.CatalogMapper;
import com.curiez.admin.service.mapper.DomainMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogService extends BasicCRUDService<Catalog, CatalogDTO> {

    @Autowired
    CatalogRepository repository;

    @Override
    ReactiveCrudRepository<Catalog, String> getRepository() {
        return repository;
    }

    @Override
    DomainMapper<Catalog, CatalogDTO> getMapper() {
        return Mappers.getMapper(CatalogMapper.class);
    }
}
