package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HospitalTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HospitalType.class);
        HospitalType hospitalType1 = new HospitalType();
        hospitalType1.setId(1L);
        HospitalType hospitalType2 = new HospitalType();
        hospitalType2.setId(hospitalType1.getId());
        assertThat(hospitalType1).isEqualTo(hospitalType2);
        hospitalType2.setId(2L);
        assertThat(hospitalType1).isNotEqualTo(hospitalType2);
        hospitalType1.setId(null);
        assertThat(hospitalType1).isNotEqualTo(hospitalType2);
    }
}
