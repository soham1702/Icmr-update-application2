package com.techvg.covid.care.service.mapper;

import com.techvg.covid.care.domain.ICMRDailyCount;
import com.techvg.covid.care.service.dto.ICMRDailyCountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ICMRDailyCount} and its DTO {@link ICMRDailyCountDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ICMRDailyCountMapper extends EntityMapper<ICMRDailyCountDTO, ICMRDailyCount> {}
