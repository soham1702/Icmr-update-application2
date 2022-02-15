package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedTransactionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BedTransactions.class);
        BedTransactions bedTransactions1 = new BedTransactions();
        bedTransactions1.setId(1L);
        BedTransactions bedTransactions2 = new BedTransactions();
        bedTransactions2.setId(bedTransactions1.getId());
        assertThat(bedTransactions1).isEqualTo(bedTransactions2);
        bedTransactions2.setId(2L);
        assertThat(bedTransactions1).isNotEqualTo(bedTransactions2);
        bedTransactions1.setId(null);
        assertThat(bedTransactions1).isNotEqualTo(bedTransactions2);
    }
}
