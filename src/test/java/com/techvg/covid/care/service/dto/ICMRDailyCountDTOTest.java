package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ICMRDailyCountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ICMRDailyCountDTO.class);
        ICMRDailyCountDTO iCMRDailyCountDTO1 = new ICMRDailyCountDTO();
        iCMRDailyCountDTO1.setId(1L);
        ICMRDailyCountDTO iCMRDailyCountDTO2 = new ICMRDailyCountDTO();
        assertThat(iCMRDailyCountDTO1).isNotEqualTo(iCMRDailyCountDTO2);
        iCMRDailyCountDTO2.setId(iCMRDailyCountDTO1.getId());
        assertThat(iCMRDailyCountDTO1).isEqualTo(iCMRDailyCountDTO2);
        iCMRDailyCountDTO2.setId(2L);
        assertThat(iCMRDailyCountDTO1).isNotEqualTo(iCMRDailyCountDTO2);
        iCMRDailyCountDTO1.setId(null);
        assertThat(iCMRDailyCountDTO1).isNotEqualTo(iCMRDailyCountDTO2);
    }
}
