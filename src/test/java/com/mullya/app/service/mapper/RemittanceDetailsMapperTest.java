package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemittanceDetailsMapperTest {

    private RemittanceDetailsMapper remittanceDetailsMapper;

    @BeforeEach
    public void setUp() {
        remittanceDetailsMapper = new RemittanceDetailsMapperImpl();
    }
}
