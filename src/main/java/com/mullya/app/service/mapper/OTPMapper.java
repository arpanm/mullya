package com.mullya.app.service.mapper;

import com.mullya.app.domain.OTP;
import com.mullya.app.service.dto.OTPDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OTP} and its DTO {@link OTPDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface OTPMapper extends EntityMapper<OTPDTO, OTP> {
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    OTPDTO toDto(OTP s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OTPDTO toDtoId(OTP oTP);
}
