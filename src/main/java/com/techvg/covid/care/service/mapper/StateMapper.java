package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.State;
import com.techvg.covid.care.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    StateDTO toDtoName(State state);
}
