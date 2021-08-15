package com.curiez.admin.route;


import com.curiez.admin.handler.CatalogHandler;
import com.curiez.admin.handler.SkuHandler;
import com.curiez.admin.model.Catalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CatalogRouter {

    @Bean
    public RouterFunction<ServerResponse> routeCatalog(CatalogHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/catalog/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::get)
                .andRoute(RequestPredicates.PUT("/catalog")
               .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::save)
                .andRoute(RequestPredicates.PATCH("/catalog")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::update)
                        .andRoute(RequestPredicates.DELETE("/catalog/{id}")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::delete)
                .andRoute(RequestPredicates.POST("/catalog")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::create);

    }
}

