package com.curiez.admin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;


import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class Product {

 @Id
 private String id;
 private String name;
 private String description;

 @Relationship(type = "has_product_image", direction = Relationship.Direction.INCOMING)
 private Set<Media> medias;

 @CompositeProperty
 @Property("text")
 private Map<String,String> attributes;

 private Double list;
 private Double sale;
 private Double special;
 @Relationship(type = "has_sku", direction = Relationship.Direction.INCOMING)
 private Set<Sku> skus;

}

