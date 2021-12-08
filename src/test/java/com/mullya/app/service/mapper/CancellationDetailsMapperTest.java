package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CancellationDetailsMapperTest {

    private CancellationDetailsMapper cancellationDetailsMapper;

    @BeforeEach
    public void setUp() {
        cancellationDetailsMapper = new CancellationDetailsMapperImpl();
    }
}
