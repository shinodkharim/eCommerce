package com.curiez.admin.dto;
import com.curiez.admin.model.Catalog;
import com.curiez.admin.model.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogDTO {
    String id;
    String name;
    Catalog parent;
    String description;
    Media media;
}
