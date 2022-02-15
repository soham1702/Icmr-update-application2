package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedInventoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedInventoryDTO.class);
        BedInventoryDTO bedInventoryDTO1 = new BedInventoryDTO();
        bedInventoryDTO1.setId(1L);
        BedInventoryDTO bedInventoryDTO2 = new BedInventoryDTO();
        assertThat(bedInventoryDTO1).isNotEqualTo(bedInventoryDTO2);
        bedInventoryDTO2.setId(bedInventoryDTO1.getId());
        assertThat(bedInventoryDTO1).isEqualTo(bedInventoryDTO2);
        bedInventoryDTO2.setId(2L);
        assertThat(bedInventoryDTO1).isNotEqualTo(bedInventoryDTO2);
        bedInventoryDTO1.setId(null);
        assertThat(bedInventoryDTO1).isNotEqualTo(bedInventoryDTO2);
    }
}
