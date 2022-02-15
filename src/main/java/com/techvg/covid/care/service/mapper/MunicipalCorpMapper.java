package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.MunicipalCorp;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MunicipalCorp} and its DTO {@link MunicipalCorpDTO}.
 */
@Mapper(componentModel = "spring", uses = { DistrictMapper.class })
public interface MunicipalCorpMapper extends EntityMapper<MunicipalCorpDTO, MunicipalCorp> {
    @Mapping(target = "district", source = "district", qualifiedByName = "name")
    MunicipalCorpDTO toDto(MunicipalCorp s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    MunicipalCorpDTO toDtoName(MunicipalCorp municipalCorp);
}
