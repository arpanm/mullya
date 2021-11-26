package com.mullya.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequirementMapperTest {

    private RequirementMapper requirementMapper;

    @BeforeEach
    public void setUp() {
        requirementMapper = new RequirementMapperImpl();
    }
}
