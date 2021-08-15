package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import com.curiez.admin.repository.ProductRepository;
import com.curiez.admin.service.mapper.DomainMapper;
import com.curiez.admin.service.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BasicCRUDService<Product, ProductDTO> {

    @Autowired
    ProductRepository repository;
    @Override
    ReactiveCrudRepository<Product, String> getRepository() {
        return repository;
    }

    @Override
    DomainMapper<Product, ProductDTO> getMapper() {
        return Mappers.getMapper( ProductMapper.class );
    }
}
