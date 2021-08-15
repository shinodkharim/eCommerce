package com.curiez.admin.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

public interface DomainMapper<E,D> {
    D toDTO(E entity);
    E  toEntity(D productDTO);
    void updateEntityFromDto(D productDTO, @MappingTarget E productEntity);
    Mapper INSTANCE = Mappers.getMapper( Mapper.class );

}
