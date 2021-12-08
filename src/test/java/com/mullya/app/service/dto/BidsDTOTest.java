package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BidsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BidsDTO.class);
        BidsDTO bidsDTO1 = new BidsDTO();
        bidsDTO1.setId(1L);
        BidsDTO bidsDTO2 = new BidsDTO();
        assertThat(bidsDTO1).isNotEqualTo(bidsDTO2);
        bidsDTO2.setId(bidsDTO1.getId());
        assertThat(bidsDTO1).isEqualTo(bidsDTO2);
        bidsDTO2.setId(2L);
        assertThat(bidsDTO1).isNotEqualTo(bidsDTO2);
        bidsDTO1.setId(null);
        assertThat(bidsDTO1).isNotEqualTo(bidsDTO2);
    }
}
