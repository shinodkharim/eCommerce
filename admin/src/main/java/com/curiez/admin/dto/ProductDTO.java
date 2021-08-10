package com.curiez.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
public class ProductDTO {
 private String id;
 private String name;
 private String description;
 private Set<MediaDTO> medias;
 private Map<String,String> attributes;
 private Double list;
 private Double sale;
 private Double special;
 private Set<SkuDTO> skus;


}

