package com.mullya.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BiddingDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiddingDetails.class);
        BiddingDetails biddingDetails1 = new BiddingDetails();
        biddingDetails1.setId(1L);
        BiddingDetails biddingDetails2 = new BiddingDetails();
        biddingDetails2.setId(biddingDetails1.getId());
        assertThat(biddingDetails1).isEqualTo(biddingDetails2);
        biddingDetails2.setId(2L);
        assertThat(biddingDetails1).isNotEqualTo(biddingDetails2);
        biddingDetails1.setId(null);
        assertThat(biddingDetails1).isNotEqualTo(biddingDetails2);
    }
}
