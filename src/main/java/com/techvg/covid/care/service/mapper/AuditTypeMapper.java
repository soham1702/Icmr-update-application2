package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.AuditType;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditType} and its DTO {@link AuditTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AuditTypeMapper extends EntityMapper<AuditTypeDTO, AuditType> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    AuditTypeDTO toDtoName(AuditType auditType);
}
