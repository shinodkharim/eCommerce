package com.curiez.model;

//import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Node("SKU")
public class SKU {

  @Id
  private String id;

  private String name;

  private String description;

  private Boolean isStock;

  private Long inventory;
  @Relationship(type = "HAS_IMAGES", direction = Relationship.Direction.INCOMING)
  private Set<Media> medias;

  @Relationship(type = "HAS_ATTRIBUTES", direction = Relationship.Direction.INCOMING)
  private Map<String,String> attributes;

  @Relationship(type = "HAS_PRICE ", direction = Relationship.Direction.INCOMING)
  private Price price;
}

