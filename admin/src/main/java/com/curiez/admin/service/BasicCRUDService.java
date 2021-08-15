package com.curiez.admin.service;

import com.curiez.admin.exception.ItemExists;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.service.mapper.DomainMapper;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public  abstract class BasicCRUDService<E,D>  {

    public Mono<D> save(D productDTO) {
        return getRepository().save(getMapper().toEntity(productDTO))
                .flatMap(p -> Mono.just(getMapper().toDTO(p)));

    }
    public Mono<D> create(D productDTO,String id) {
        return getRepository().existsById(id)
                .flatMap(isPresent -> isPresent ?  Mono.error(ItemExists::new) :
                        getRepository()
                                .save(getMapper().toEntity(productDTO))
                                .flatMap(p -> Mono.just(getMapper().toDTO(p))));
    }

    public Mono<D> find(String id) {
        return getRepository().findById(id)
                .flatMap( p ->  Mono.just(getMapper().toDTO(p))
        ).switchIfEmpty(Mono.error(ItemNotFound::new));

    }

    public Mono<D> update(D pDto,String id) {
         return getRepository()
                 .findById(id)
                 .map( product-> {
                     getMapper().updateEntityFromDto(pDto,product);
                     return product;})
                 .flatMap(p-> getRepository().save(p).
                         flatMap(pr -> Mono.just(getMapper().toDTO(pr))))
                 .switchIfEmpty(Mono.error(ItemNotFound::new));
    }

    public Mono<Void> delete(String id){
       return  getRepository().findById(id)
                .switchIfEmpty(Mono.error(ItemNotFound::new))
               .flatMap(p -> getRepository().delete(p));


    }

   abstract ReactiveCrudRepository<E,String>  getRepository();

   abstract DomainMapper<E,D> getMapper();

}



