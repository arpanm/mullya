package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BiddingDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BiddingDetailsDTO.class);
        BiddingDetailsDTO biddingDetailsDTO1 = new BiddingDetailsDTO();
        biddingDetailsDTO1.setId(1L);
        BiddingDetailsDTO biddingDetailsDTO2 = new BiddingDetailsDTO();
        assertThat(biddingDetailsDTO1).isNotEqualTo(biddingDetailsDTO2);
        biddingDetailsDTO2.setId(biddingDetailsDTO1.getId());
        assertThat(biddingDetailsDTO1).isEqualTo(biddingDetailsDTO2);
        biddingDetailsDTO2.setId(2L);
        assertThat(biddingDetailsDTO1).isNotEqualTo(biddingDetailsDTO2);
        biddingDetailsDTO1.setId(null);
        assertThat(biddingDetailsDTO1).isNotEqualTo(biddingDetailsDTO2);
    }
}
