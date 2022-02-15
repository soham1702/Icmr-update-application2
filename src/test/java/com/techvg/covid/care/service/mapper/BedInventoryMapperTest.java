package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedInventoryMapperTest {

    private BedInventoryMapper bedInventoryMapper;

    @BeforeEach
    public void setUp() {
        bedInventoryMapper = new BedInventoryMapperImpl();
    }
}
