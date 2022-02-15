package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientInfoMapperTest {

    private PatientInfoMapper patientInfoMapper;

    @BeforeEach
    public void setUp() {
        patientInfoMapper = new PatientInfoMapperImpl();
    }
}
