package com.techvg.covid.care.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.covid.care.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TripDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripDetails.class);
        TripDetails tripDetails1 = new TripDetails();
        tripDetails1.setId(1L);
        TripDetails tripDetails2 = new TripDetails();
        tripDetails2.setId(tripDetails1.getId());
        assertThat(tripDetails1).isEqualTo(tripDetails2);
        tripDetails2.setId(2L);
        assertThat(tripDetails1).isNotEqualTo(tripDetails2);
        tripDetails1.setId(null);
        assertThat(tripDetails1).isNotEqualTo(tripDetails2);
    }
}
