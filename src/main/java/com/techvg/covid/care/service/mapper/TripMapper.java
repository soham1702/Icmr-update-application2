package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.Trip;
import com.techvg.covid.care.service.dto.TripDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Trip} and its DTO {@link TripDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupplierMapper.class })
public interface TripMapper extends EntityMapper<TripDTO, Trip> {
    @Mapping(target = "supplier", source = "supplier", qualifiedByName = "name")
    TripDTO toDto(Trip s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TripDTO toDtoId(Trip trip);
}
