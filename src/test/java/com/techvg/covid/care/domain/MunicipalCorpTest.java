package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MunicipalCorpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunicipalCorp.class);
        MunicipalCorp municipalCorp1 = new MunicipalCorp();
        municipalCorp1.setId(1L);
        MunicipalCorp municipalCorp2 = new MunicipalCorp();
        municipalCorp2.setId(municipalCorp1.getId());
        assertThat(municipalCorp1).isEqualTo(municipalCorp2);
        municipalCorp2.setId(2L);
        assertThat(municipalCorp1).isNotEqualTo(municipalCorp2);
        municipalCorp1.setId(null);
        assertThat(municipalCorp1).isNotEqualTo(municipalCorp2);
    }
}
