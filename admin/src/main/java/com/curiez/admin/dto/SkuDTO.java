package com.curiez.admin.dto;

//import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.neo4j.core.schema.CompositeProperty;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class SkuDTO {
  private String id;
  private String name;
  private String description;
  private Boolean isStock;
  private Long inventory;
  private Double list;
  private Double sale;
  private Double special;
  private Set<MediaDTO> medias;
  private Map<String,String> attributes;
}

