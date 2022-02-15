package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryMaster.class);
        InventoryMaster inventoryMaster1 = new InventoryMaster();
        inventoryMaster1.setId(1L);
        InventoryMaster inventoryMaster2 = new InventoryMaster();
        inventoryMaster2.setId(inventoryMaster1.getId());
        assertThat(inventoryMaster1).isEqualTo(inventoryMaster2);
        inventoryMaster2.setId(2L);
        assertThat(inventoryMaster1).isNotEqualTo(inventoryMaster2);
        inventoryMaster1.setId(null);
        assertThat(inventoryMaster1).isNotEqualTo(inventoryMaster2);
    }
}
