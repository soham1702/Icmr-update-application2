package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.ContactType;
import com.techvg.covid.care.service.dto.ContactTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactType} and its DTO {@link ContactTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactTypeMapper extends EntityMapper<ContactTypeDTO, ContactType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ContactTypeDTO toDtoName(ContactType contactType);
}
