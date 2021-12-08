package com.mullya.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CancellationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationDetailsDTO.class);
        CancellationDetailsDTO cancellationDetailsDTO1 = new CancellationDetailsDTO();
        cancellationDetailsDTO1.setId(1L);
        CancellationDetailsDTO cancellationDetailsDTO2 = new CancellationDetailsDTO();
        assertThat(cancellationDetailsDTO1).isNotEqualTo(cancellationDetailsDTO2);
        cancellationDetailsDTO2.setId(cancellationDetailsDTO1.getId());
        assertThat(cancellationDetailsDTO1).isEqualTo(cancellationDetailsDTO2);
        cancellationDetailsDTO2.setId(2L);
        assertThat(cancellationDetailsDTO1).isNotEqualTo(cancellationDetailsDTO2);
        cancellationDetailsDTO1.setId(null);
        assertThat(cancellationDetailsDTO1).isNotEqualTo(cancellationDetailsDTO2);
    }
}
