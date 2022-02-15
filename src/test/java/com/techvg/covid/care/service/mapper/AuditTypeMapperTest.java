package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditTypeMapperTest {

    private AuditTypeMapper auditTypeMapper;

    @BeforeEach
    public void setUp() {
        auditTypeMapper = new AuditTypeMapperImpl();
    }
}
