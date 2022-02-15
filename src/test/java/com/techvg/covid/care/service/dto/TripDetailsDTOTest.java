package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TripDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripDetailsDTO.class);
        TripDetailsDTO tripDetailsDTO1 = new TripDetailsDTO();
        tripDetailsDTO1.setId(1L);
        TripDetailsDTO tripDetailsDTO2 = new TripDetailsDTO();
        assertThat(tripDetailsDTO1).isNotEqualTo(tripDetailsDTO2);
        tripDetailsDTO2.setId(tripDetailsDTO1.getId());
        assertThat(tripDetailsDTO1).isEqualTo(tripDetailsDTO2);
        tripDetailsDTO2.setId(2L);
        assertThat(tripDetailsDTO1).isNotEqualTo(tripDetailsDTO2);
        tripDetailsDTO1.setId(null);
        assertThat(tripDetailsDTO1).isNotEqualTo(tripDetailsDTO2);
    }
}
