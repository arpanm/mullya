package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OTPDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTPDTO.class);
        OTPDTO oTPDTO1 = new OTPDTO();
        oTPDTO1.setId(1L);
        OTPDTO oTPDTO2 = new OTPDTO();
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
        oTPDTO2.setId(oTPDTO1.getId());
        assertThat(oTPDTO1).isEqualTo(oTPDTO2);
        oTPDTO2.setId(2L);
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
        oTPDTO1.setId(null);
        assertThat(oTPDTO1).isNotEqualTo(oTPDTO2);
    }
}
