package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PatientInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientInfoDTO.class);
        PatientInfoDTO patientInfoDTO1 = new PatientInfoDTO();
        patientInfoDTO1.setId(1L);
        PatientInfoDTO patientInfoDTO2 = new PatientInfoDTO();
        assertThat(patientInfoDTO1).isNotEqualTo(patientInfoDTO2);
        patientInfoDTO2.setId(patientInfoDTO1.getId());
        assertThat(patientInfoDTO1).isEqualTo(patientInfoDTO2);
        patientInfoDTO2.setId(2L);
        assertThat(patientInfoDTO1).isNotEqualTo(patientInfoDTO2);
        patientInfoDTO1.setId(null);
        assertThat(patientInfoDTO1).isNotEqualTo(patientInfoDTO2);
    }
}
