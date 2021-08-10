package com.curiez.admin.handler;

import com.curiez.admin.dto.ProductDTO;
import com.curiez.admin.model.Product;
import com.curiez.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ProductHandler {

    @Autowired
    ProductService service;

    public Mono<ServerResponse> getProduct(ServerRequest request) {
        return service.getProduct(request.pathVariable("id")).flatMap(
                p-> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build()));

    }
/*
    public Mono<ServerResponse> getSku(ServerRequest request) {
        final Mono<Sku> sku = repository.findSkuById(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(sku, Sku.class));

    }
*/
   public Mono<ServerResponse> saveProduct(ServerRequest request) {
      final  Mono<ProductDTO> productDTOMono = request.bodyToMono(ProductDTO.class);
        return productDTOMono.flatMap(p ->
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(service.saveProduct(p), ProductDTO.class)
        );

    }

    public Mono<ServerResponse> updateProduct(ServerRequest request){
       Mono<ProductDTO> productDTOMono = request.bodyToMono(ProductDTO.class);
       return productDTOMono.flatMap(p -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
               .body(service.updateProduct(p),ProductDTO.class));
        }


    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
       return ServerResponse.noContent().build(service.deleteProduct(request.pathVariable("id")));
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        final  Mono<ProductDTO> productDTOMono = request.bodyToMono(ProductDTO.class);
        return productDTOMono.flatMap(p ->
                ServerResponse.created(request.uri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.createProduct(p), ProductDTO.class)
        );

    }
}
