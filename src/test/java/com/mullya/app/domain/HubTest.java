package com.mullya.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mullya.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HubTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hub.class);
        Hub hub1 = new Hub();
        hub1.setId(1L);
        Hub hub2 = new Hub();
        hub2.setId(hub1.getId());
        assertThat(hub1).isEqualTo(hub2);
        hub2.setId(2L);
        assertThat(hub1).isNotEqualTo(hub2);
        hub1.setId(null);
        assertThat(hub1).isNotEqualTo(hub2);
    }
}
