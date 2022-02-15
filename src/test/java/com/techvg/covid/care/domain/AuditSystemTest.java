package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuditSystemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditSystem.class);
        AuditSystem auditSystem1 = new AuditSystem();
        auditSystem1.setId(1L);
        AuditSystem auditSystem2 = new AuditSystem();
        auditSystem2.setId(auditSystem1.getId());
        assertThat(auditSystem1).isEqualTo(auditSystem2);
        auditSystem2.setId(2L);
        assertThat(auditSystem1).isNotEqualTo(auditSystem2);
        auditSystem1.setId(null);
        assertThat(auditSystem1).isNotEqualTo(auditSystem2);
    }
}
