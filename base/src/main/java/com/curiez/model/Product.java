package com.curiez.model;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Product")
public class Product   {

  @Id
  private String id;

  private String name;

  private String description;

  @Relationship(type = "HAS_IMAGES", direction = Relationship.Direction.INCOMING)
  private Set<Media> medias;

  @Relationship(type = "HAS_ATTRIBUTES", direction = Relationship.Direction.INCOMING)
  private Map<String,String> attributes;

  @Relationship(type = "HAS_SKUS", direction = Relationship.Direction.INCOMING)
  private Set<SKU> skus;

}

