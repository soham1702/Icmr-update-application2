package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BedType} and its DTO {@link BedTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BedTypeMapper extends EntityMapper<BedTypeDTO, BedType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BedTypeDTO toDtoName(BedType bedType);
}
