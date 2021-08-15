package com.curiez.admin.service;

import reactor.core.publisher.Mono;

public interface CRUDService<E,D> {
     Mono<D> save(D productDTO);
     Mono<D> create(D productDTO,String id);
     Mono<D> find(String id);
     Mono<D> update(D pDto,String id);
     Mono<Void> delete(String id);



}
