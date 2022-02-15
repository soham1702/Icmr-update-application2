package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactTypeMapperTest {

    private ContactTypeMapper contactTypeMapper;

    @BeforeEach
    public void setUp() {
        contactTypeMapper = new ContactTypeMapperImpl();
    }
}
