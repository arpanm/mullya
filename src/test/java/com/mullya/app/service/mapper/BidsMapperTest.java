package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BidsMapperTest {

    private BidsMapper bidsMapper;

    @BeforeEach
    public void setUp() {
        bidsMapper = new BidsMapperImpl();
    }
}
