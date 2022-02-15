package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ICMRDailyCountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ICMRDailyCount.class);
        ICMRDailyCount iCMRDailyCount1 = new ICMRDailyCount();
        iCMRDailyCount1.setId(1L);
        ICMRDailyCount iCMRDailyCount2 = new ICMRDailyCount();
        iCMRDailyCount2.setId(iCMRDailyCount1.getId());
        assertThat(iCMRDailyCount1).isEqualTo(iCMRDailyCount2);
        iCMRDailyCount2.setId(2L);
        assertThat(iCMRDailyCount1).isNotEqualTo(iCMRDailyCount2);
        iCMRDailyCount1.setId(null);
        assertThat(iCMRDailyCount1).isNotEqualTo(iCMRDailyCount2);
    }
}
