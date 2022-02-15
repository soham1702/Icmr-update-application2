package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventoryUsedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InventoryUsed.class);
        InventoryUsed inventoryUsed1 = new InventoryUsed();
        inventoryUsed1.setId(1L);
        InventoryUsed inventoryUsed2 = new InventoryUsed();
        inventoryUsed2.setId(inventoryUsed1.getId());
        assertThat(inventoryUsed1).isEqualTo(inventoryUsed2);
        inventoryUsed2.setId(2L);
        assertThat(inventoryUsed1).isNotEqualTo(inventoryUsed2);
        inventoryUsed1.setId(null);
        assertThat(inventoryUsed1).isNotEqualTo(inventoryUsed2);
    }
}
