package com.mullya.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BidsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bids.class);
        Bids bids1 = new Bids();
        bids1.setId(1L);
        Bids bids2 = new Bids();
        bids2.setId(bids1.getId());
        assertThat(bids1).isEqualTo(bids2);
        bids2.setId(2L);
        assertThat(bids1).isNotEqualTo(bids2);
        bids1.setId(null);
        assertThat(bids1).isNotEqualTo(bids2);
    }
}
