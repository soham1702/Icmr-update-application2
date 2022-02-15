package com.techvg.covid.care.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuditSystemMapperTest {

    private AuditSystemMapper auditSystemMapper;

    @BeforeEach
    public void setUp() {
        auditSystemMapper = new AuditSystemMapperImpl();
    }
}
