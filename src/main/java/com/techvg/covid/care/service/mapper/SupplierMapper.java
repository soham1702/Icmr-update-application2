package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.service.dto.SupplierDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Supplier} and its DTO {@link SupplierDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { StateMapper.class, DistrictMapper.class, TalukaMapper.class, CityMapper.class, InventoryTypeMapper.class }
)
public interface SupplierMapper extends EntityMapper<SupplierDTO, Supplier> {
    @Mapping(target = "state", source = "state", qualifiedByName = "name")
    @Mapping(target = "district", source = "district", qualifiedByName = "name")
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "name")
    @Mapping(target = "city", source = "city", qualifiedByName = "name")
    @Mapping(target = "inventoryType", source = "inventoryType", qualifiedByName = "name")
    SupplierDTO toDto(Supplier s);

    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<SupplierDTO> toDtoNameSet(Set<Supplier> supplier);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SupplierDTO toDtoName(Supplier supplier);
}
