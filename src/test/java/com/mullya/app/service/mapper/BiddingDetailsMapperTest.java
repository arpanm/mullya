package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BiddingDetailsMapperTest {

    private BiddingDetailsMapper biddingDetailsMapper;

    @BeforeEach
    public void setUp() {
        biddingDetailsMapper = new BiddingDetailsMapperImpl();
    }
}
