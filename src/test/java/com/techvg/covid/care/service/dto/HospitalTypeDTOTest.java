package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HospitalTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospitalTypeDTO.class);
        HospitalTypeDTO hospitalTypeDTO1 = new HospitalTypeDTO();
        hospitalTypeDTO1.setId(1L);
        HospitalTypeDTO hospitalTypeDTO2 = new HospitalTypeDTO();
        assertThat(hospitalTypeDTO1).isNotEqualTo(hospitalTypeDTO2);
        hospitalTypeDTO2.setId(hospitalTypeDTO1.getId());
        assertThat(hospitalTypeDTO1).isEqualTo(hospitalTypeDTO2);
        hospitalTypeDTO2.setId(2L);
        assertThat(hospitalTypeDTO1).isNotEqualTo(hospitalTypeDTO2);
        hospitalTypeDTO1.setId(null);
        assertThat(hospitalTypeDTO1).isNotEqualTo(hospitalTypeDTO2);
    }
}
