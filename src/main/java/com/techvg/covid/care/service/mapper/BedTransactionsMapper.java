package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.BedTransactions;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BedTransactions} and its DTO {@link BedTransactionsDTO}.
 */
@Mapper(componentModel = "spring", uses = { BedTypeMapper.class, HospitalMapper.class })
public interface BedTransactionsMapper extends EntityMapper<BedTransactionsDTO, BedTransactions> {
    @Mapping(target = "bedType", source = "bedType", qualifiedByName = "name")
    @Mapping(target = "hospital", source = "hospital", qualifiedByName = "name")
    BedTransactionsDTO toDto(BedTransactions s);
}
