package com.mullya.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemittanceDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemittanceDetails.class);
        RemittanceDetails remittanceDetails1 = new RemittanceDetails();
        remittanceDetails1.setId(1L);
        RemittanceDetails remittanceDetails2 = new RemittanceDetails();
        remittanceDetails2.setId(remittanceDetails1.getId());
        assertThat(remittanceDetails1).isEqualTo(remittanceDetails2);
        remittanceDetails2.setId(2L);
        assertThat(remittanceDetails1).isNotEqualTo(remittanceDetails2);
        remittanceDetails1.setId(null);
        assertThat(remittanceDetails1).isNotEqualTo(remittanceDetails2);
    }
}
