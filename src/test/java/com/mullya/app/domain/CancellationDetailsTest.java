package com.mullya.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CancellationDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancellationDetails.class);
        CancellationDetails cancellationDetails1 = new CancellationDetails();
        cancellationDetails1.setId(1L);
        CancellationDetails cancellationDetails2 = new CancellationDetails();
        cancellationDetails2.setId(cancellationDetails1.getId());
        assertThat(cancellationDetails1).isEqualTo(cancellationDetails2);
        cancellationDetails2.setId(2L);
        assertThat(cancellationDetails1).isNotEqualTo(cancellationDetails2);
        cancellationDetails1.setId(null);
        assertThat(cancellationDetails1).isNotEqualTo(cancellationDetails2);
    }
}
