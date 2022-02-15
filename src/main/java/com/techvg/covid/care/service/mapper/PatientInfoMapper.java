package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.service.dto.PatientInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientInfo} and its DTO {@link PatientInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { StateMapper.class, DistrictMapper.class })
public interface PatientInfoMapper extends EntityMapper<PatientInfoDTO, PatientInfo> {
    @Mapping(target = "state", source = "state", qualifiedByName = "name")
    @Mapping(target = "district", source = "district", qualifiedByName = "name")
    PatientInfoDTO toDto(PatientInfo s);
}
