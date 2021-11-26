package com.mullya.app.service.mapper;

import com.mullya.app.domain.OTPAttempt;
import com.mullya.app.service.dto.OTPAttemptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OTPAttempt} and its DTO {@link OTPAttemptDTO}.
 */
@Mapper(componentModel = "spring", uses = { OTPMapper.class })
public interface OTPAttemptMapper extends EntityMapper<OTPAttemptDTO, OTPAttempt> {
    @Mapping(target = "otp", source = "otp", qualifiedByName = "id")
    OTPAttemptDTO toDto(OTPAttempt s);
}
