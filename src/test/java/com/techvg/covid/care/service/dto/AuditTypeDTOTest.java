package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditTypeDTO.class);
        AuditTypeDTO auditTypeDTO1 = new AuditTypeDTO();
        auditTypeDTO1.setId(1L);
        AuditTypeDTO auditTypeDTO2 = new AuditTypeDTO();
        assertThat(auditTypeDTO1).isNotEqualTo(auditTypeDTO2);
        auditTypeDTO2.setId(auditTypeDTO1.getId());
        assertThat(auditTypeDTO1).isEqualTo(auditTypeDTO2);
        auditTypeDTO2.setId(2L);
        assertThat(auditTypeDTO1).isNotEqualTo(auditTypeDTO2);
        auditTypeDTO1.setId(null);
        assertThat(auditTypeDTO1).isNotEqualTo(auditTypeDTO2);
    }
}
