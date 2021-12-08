package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HubMapperTest {

    private HubMapper hubMapper;

    @BeforeEach
    public void setUp() {
        hubMapper = new HubMapperImpl();
    }
}
