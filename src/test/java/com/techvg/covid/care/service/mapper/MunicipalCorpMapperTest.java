package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MunicipalCorpMapperTest {

    private MunicipalCorpMapper municipalCorpMapper;

    @BeforeEach
    public void setUp() {
        municipalCorpMapper = new MunicipalCorpMapperImpl();
    }
}
