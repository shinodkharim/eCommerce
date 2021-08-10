package com.curiez.admin.route;


import com.curiez.admin.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> route(ProductHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/product/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getProduct)
               // .andRoute(RequestPredicates.GET("/sku/{id}")
               // .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),handler::getSku)
                .andRoute(RequestPredicates.PUT("/product")
               .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::saveProduct)
                .andRoute(RequestPredicates.PATCH("/product")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::updateProduct)
                        .andRoute(RequestPredicates.DELETE("/product/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::deleteProduct)
                .andRoute(RequestPredicates.POST("/product")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::createProduct);

    }
}
