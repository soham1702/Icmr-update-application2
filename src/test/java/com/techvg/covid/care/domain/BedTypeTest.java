package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedType.class);
        BedType bedType1 = new BedType();
        bedType1.setId(1L);
        BedType bedType2 = new BedType();
        bedType2.setId(bedType1.getId());
        assertThat(bedType1).isEqualTo(bedType2);
        bedType2.setId(2L);
        assertThat(bedType1).isNotEqualTo(bedType2);
        bedType1.setId(null);
        assertThat(bedType1).isNotEqualTo(bedType2);
    }
}
