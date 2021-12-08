package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemittanceDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemittanceDetailsDTO.class);
        RemittanceDetailsDTO remittanceDetailsDTO1 = new RemittanceDetailsDTO();
        remittanceDetailsDTO1.setId(1L);
        RemittanceDetailsDTO remittanceDetailsDTO2 = new RemittanceDetailsDTO();
        assertThat(remittanceDetailsDTO1).isNotEqualTo(remittanceDetailsDTO2);
        remittanceDetailsDTO2.setId(remittanceDetailsDTO1.getId());
        assertThat(remittanceDetailsDTO1).isEqualTo(remittanceDetailsDTO2);
        remittanceDetailsDTO2.setId(2L);
        assertThat(remittanceDetailsDTO1).isNotEqualTo(remittanceDetailsDTO2);
        remittanceDetailsDTO1.setId(null);
        assertThat(remittanceDetailsDTO1).isNotEqualTo(remittanceDetailsDTO2);
    }
}
