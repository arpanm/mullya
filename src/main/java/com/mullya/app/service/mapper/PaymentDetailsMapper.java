package com.mullya.app.service.mapper;

import com.mullya.app.domain.PaymentDetails;
import com.mullya.app.service.dto.PaymentDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentDetails} and its DTO {@link PaymentDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface PaymentDetailsMapper extends EntityMapper<PaymentDetailsDTO, PaymentDetails> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    PaymentDetailsDTO toDto(PaymentDetails s);
}
