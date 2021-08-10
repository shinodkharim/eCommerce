package com.curiez.admin.repository;


import com.curiez.admin.model.Product;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
public interface ProductRepository extends ReactiveNeo4jRepository<Product,String> {

}
