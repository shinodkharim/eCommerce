package com.curiez.admin.service;

import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.model.Sku;
import com.curiez.admin.repository.SkuRepository;
import com.curiez.admin.service.mapper.DomainMapper;
import com.curiez.admin.service.mapper.SkuMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

@Service
public class SkuService extends BasicCRUDService<Sku, SkuDTO> {

    @Autowired
    SkuRepository repository;

    @Override
    ReactiveCrudRepository<Sku, String> getRepository() {
        return repository;
    }

    @Override
    DomainMapper<Sku, SkuDTO> getMapper() {
        return Mappers.getMapper(SkuMapper.class);
    }
}
