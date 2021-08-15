package com.curiez.admin.repository;

import com.curiez.admin.model.Sku;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface SkuRepository extends ReactiveNeo4jRepository<Sku,String> {
}
