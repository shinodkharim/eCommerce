package com.curiez.admin.route;


import com.curiez.admin.handler.SkuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SkuRouter {

    @Bean
    public RouterFunction<ServerResponse> routeSku(SkuHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/sku/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::get)
                .andRoute(RequestPredicates.PUT("/sku")
               .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::save)
                .andRoute(RequestPredicates.PATCH("/sku")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::update)
                        .andRoute(RequestPredicates.DELETE("/sku/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::delete)
                .andRoute(RequestPredicates.POST("/sku")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::create);

    }
}

