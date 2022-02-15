package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedInventoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedInventory.class);
        BedInventory bedInventory1 = new BedInventory();
        bedInventory1.setId(1L);
        BedInventory bedInventory2 = new BedInventory();
        bedInventory2.setId(bedInventory1.getId());
        assertThat(bedInventory1).isEqualTo(bedInventory2);
        bedInventory2.setId(2L);
        assertThat(bedInventory1).isNotEqualTo(bedInventory2);
        bedInventory1.setId(null);
        assertThat(bedInventory1).isNotEqualTo(bedInventory2);
    }
}
