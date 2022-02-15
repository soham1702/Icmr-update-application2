package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.service.dto.HospitalDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hospital} and its DTO {@link HospitalDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        StateMapper.class,
        DistrictMapper.class,
        TalukaMapper.class,
        CityMapper.class,
        MunicipalCorpMapper.class,
        HospitalTypeMapper.class,
        SupplierMapper.class,
    }
)
public interface HospitalMapper extends EntityMapper<HospitalDTO, Hospital> {
    @Mapping(target = "state", source = "state", qualifiedByName = "name")
    @Mapping(target = "district", source = "district", qualifiedByName = "name")
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "name")
    @Mapping(target = "city", source = "city", qualifiedByName = "name")
    @Mapping(target = "municipalCorp", source = "municipalCorp", qualifiedByName = "name")
    @Mapping(target = "hospitalType", source = "hospitalType", qualifiedByName = "name")
    @Mapping(target = "suppliers", source = "suppliers", qualifiedByName = "nameSet")
    HospitalDTO toDto(Hospital s);

    @Mapping(target = "removeSupplier", ignore = true)
    Hospital toEntity(HospitalDTO hospitalDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    HospitalDTO toDtoName(Hospital hospital);
}
