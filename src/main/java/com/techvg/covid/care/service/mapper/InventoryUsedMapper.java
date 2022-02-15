package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.InventoryUsed;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InventoryUsed} and its DTO {@link InventoryUsedDTO}.
 */
@Mapper(componentModel = "spring", uses = { InventoryMapper.class })
public interface InventoryUsedMapper extends EntityMapper<InventoryUsedDTO, InventoryUsed> {
    @Mapping(target = "inventory", source = "inventory", qualifiedByName = "id")
    InventoryUsedDTO toDto(InventoryUsed s);
}
