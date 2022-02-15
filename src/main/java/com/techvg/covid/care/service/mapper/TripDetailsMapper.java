package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.TripDetails;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TripDetails} and its DTO {@link TripDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { SupplierMapper.class, HospitalMapper.class, TransactionsMapper.class, TripMapper.class })
public interface TripDetailsMapper extends EntityMapper<TripDetailsDTO, TripDetails> {
    @Mapping(target = "supplier", source = "supplier", qualifiedByName = "name")
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "name")
    @Mapping(target = "transactions", source = "transactions", qualifiedByName = "id")
    @Mapping(target = "trip", source = "trip", qualifiedByName = "id")
    TripDetailsDTO toDto(TripDetails s);
}
