package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryUsedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryUsedDTO.class);
        InventoryUsedDTO inventoryUsedDTO1 = new InventoryUsedDTO();
        inventoryUsedDTO1.setId(1L);
        InventoryUsedDTO inventoryUsedDTO2 = new InventoryUsedDTO();
        assertThat(inventoryUsedDTO1).isNotEqualTo(inventoryUsedDTO2);
        inventoryUsedDTO2.setId(inventoryUsedDTO1.getId());
        assertThat(inventoryUsedDTO1).isEqualTo(inventoryUsedDTO2);
        inventoryUsedDTO2.setId(2L);
        assertThat(inventoryUsedDTO1).isNotEqualTo(inventoryUsedDTO2);
        inventoryUsedDTO1.setId(null);
        assertThat(inventoryUsedDTO1).isNotEqualTo(inventoryUsedDTO2);
    }
}
