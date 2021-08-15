package com.curiez.admin.repository;

import com.curiez.admin.model.Catalog;
import com.curiez.admin.model.Sku;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface CatalogRepository extends ReactiveNeo4jRepository<Catalog,String> {
}
