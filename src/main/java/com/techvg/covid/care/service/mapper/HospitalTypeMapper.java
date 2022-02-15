package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.HospitalType;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HospitalType} and its DTO {@link HospitalTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HospitalTypeMapper extends EntityMapper<HospitalTypeDTO, HospitalType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HospitalTypeDTO toDtoName(HospitalType hospitalType);
}
