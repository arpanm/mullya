package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OTPAttemptDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OTPAttemptDTO.class);
        OTPAttemptDTO oTPAttemptDTO1 = new OTPAttemptDTO();
        oTPAttemptDTO1.setId(1L);
        OTPAttemptDTO oTPAttemptDTO2 = new OTPAttemptDTO();
        assertThat(oTPAttemptDTO1).isNotEqualTo(oTPAttemptDTO2);
        oTPAttemptDTO2.setId(oTPAttemptDTO1.getId());
        assertThat(oTPAttemptDTO1).isEqualTo(oTPAttemptDTO2);
        oTPAttemptDTO2.setId(2L);
        assertThat(oTPAttemptDTO1).isNotEqualTo(oTPAttemptDTO2);
        oTPAttemptDTO1.setId(null);
        assertThat(oTPAttemptDTO1).isNotEqualTo(oTPAttemptDTO2);
    }
}
