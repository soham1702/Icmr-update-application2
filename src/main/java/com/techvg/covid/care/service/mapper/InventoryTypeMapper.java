package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryType} and its DTO {@link InventoryTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InventoryTypeMapper extends EntityMapper<InventoryTypeDTO, InventoryType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    InventoryTypeDTO toDtoName(InventoryType inventoryType);
}
