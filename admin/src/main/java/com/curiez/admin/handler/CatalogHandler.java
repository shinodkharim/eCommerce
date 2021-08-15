package com.curiez.admin.handler;

import com.curiez.admin.dto.CatalogDTO;
import com.curiez.admin.dto.SkuDTO;
import com.curiez.admin.exception.ItemExists;
import com.curiez.admin.exception.ItemNotFound;
import com.curiez.admin.service.CatalogService;
import com.curiez.admin.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class CatalogHandler {

    @Autowired
    CatalogService service;

    public Mono<ServerResponse> get(ServerRequest request) {
        return service.find(request.pathVariable("id"))
                .flatMap(
                        p-> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(fromValue(p)))
                .onErrorResume(ItemNotFound.class, error -> ServerResponse.noContent().build());

    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final  Mono<CatalogDTO> skuMono = request.bodyToMono(CatalogDTO.class);
        return skuMono.flatMap(p -> service.save(p).flatMap( pr ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(pr))));
    }

    public Mono<ServerResponse> update(ServerRequest request){
        final Mono<CatalogDTO> skuMono = request.bodyToMono(CatalogDTO.class);
        return skuMono.flatMap(p -> service.update(p,p.getId()).flatMap(pr -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromValue(pr)))
                .onErrorResume(ItemNotFound.class,error -> ServerResponse.notFound().build()));
    }


    public Mono<ServerResponse> delete(ServerRequest request) {
        return service.delete(request.pathVariable("id")).flatMap(p -> ServerResponse.ok().build())
                .onErrorResume(ItemNotFound.class,error -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        final  Mono<CatalogDTO> skuMono = request.bodyToMono(CatalogDTO.class);
        return skuMono.flatMap(p -> service.create(p,p.getId()).flatMap(pr -> ServerResponse.created(request.uri())
                        .contentType(MediaType.APPLICATION_JSON).body(fromValue(pr)))
                .onErrorResume(ItemExists.class, error -> ServerResponse.badRequest().build()));
    }
}
