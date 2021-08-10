package com.curiez.admin.service;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.model.Product;
import com.curiez.admin.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Mono<ProductDTO> saveProduct(ProductDTO productDTO) {
        return productRepository.save(ProductMapper.INSTANCE.toEntity(productDTO))
                .flatMap(p -> Mono.just(ProductMapper.INSTANCE.toDTO(p)));

    }
    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        return productRepository.existsById(productDTO.getId())
                .flatMap(isPresent -> isPresent == true ?  Mono.error(ItemNotFound::new) :
                         productRepository
                                .save(ProductMapper.INSTANCE.toEntity(productDTO))
                                .flatMap(p -> Mono.just(ProductMapper.INSTANCE.toDTO(p))));
                    }


    public Mono<ProductDTO> getProduct(String id) {
        return productRepository.findById(id)
                .flatMap( p ->  Mono.just(ProductMapper.INSTANCE.toDTO(p))
        ).switchIfEmpty(Mono.error(ItemNotFound::new));

    }

    public Mono<ProductDTO> updateProduct(ProductDTO pDto) {
         return productRepository
                 .findById(pDto.getId())
                 .map( product-> {
                     ProductMapper.INSTANCE.updateProductFromDto(pDto,product);
                     return product;})
                 .flatMap(p-> productRepository.save(p).
                         flatMap(pr -> Mono.just(ProductMapper.INSTANCE.toDTO(pr))))
                 .switchIfEmpty(Mono.error(ItemNotFound::new));
    }

    public Mono<Void> deleteProduct(String id){
       return  productRepository.findById(id)
                .switchIfEmpty(Mono.error(ItemNotFound::new))
               .flatMap(p -> productRepository.delete(p));


    }


}



