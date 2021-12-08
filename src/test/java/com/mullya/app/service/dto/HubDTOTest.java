package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HubDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HubDTO.class);
        HubDTO hubDTO1 = new HubDTO();
        hubDTO1.setId(1L);
        HubDTO hubDTO2 = new HubDTO();
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
        hubDTO2.setId(hubDTO1.getId());
        assertThat(hubDTO1).isEqualTo(hubDTO2);
        hubDTO2.setId(2L);
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
        hubDTO1.setId(null);
        assertThat(hubDTO1).isNotEqualTo(hubDTO2);
    }
}
