package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BedTransactionsMapperTest {

    private BedTransactionsMapper bedTransactionsMapper;

    @BeforeEach
    public void setUp() {
        bedTransactionsMapper = new BedTransactionsMapperImpl();
    }
}
