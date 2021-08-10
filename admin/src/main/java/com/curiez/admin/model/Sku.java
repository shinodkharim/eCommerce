package com.curiez.admin.model;

//import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Node("sku")
public class Sku{

  @Id
  private String id;
  private String name;
  private String description;
  private Boolean isStock;
  private Long inventory;
  private Double list;
  private Double sale;
  private Double special;
  @Relationship(type = "has_sku_image", direction = Relationship.Direction.INCOMING)
  private Set<Media> medias;
  @CompositeProperty
  private Map<String,String> attributes;
}

