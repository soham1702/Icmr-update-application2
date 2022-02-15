package com.techvg.covid.care.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryTypeDTO.class);
        InventoryTypeDTO inventoryTypeDTO1 = new InventoryTypeDTO();
        inventoryTypeDTO1.setId(1L);
        InventoryTypeDTO inventoryTypeDTO2 = new InventoryTypeDTO();
        assertThat(inventoryTypeDTO1).isNotEqualTo(inventoryTypeDTO2);
        inventoryTypeDTO2.setId(inventoryTypeDTO1.getId());
        assertThat(inventoryTypeDTO1).isEqualTo(inventoryTypeDTO2);
        inventoryTypeDTO2.setId(2L);
        assertThat(inventoryTypeDTO1).isNotEqualTo(inventoryTypeDTO2);
        inventoryTypeDTO1.setId(null);
        assertThat(inventoryTypeDTO1).isNotEqualTo(inventoryTypeDTO2);
    }
}
