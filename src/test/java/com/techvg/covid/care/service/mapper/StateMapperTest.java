package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StateMapperTest {

    private StateMapper stateMapper;

    @BeforeEach
    public void setUp() {
        stateMapper = new StateMapperImpl();
    }
}
