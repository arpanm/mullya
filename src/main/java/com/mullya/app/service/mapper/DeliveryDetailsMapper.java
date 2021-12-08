package com.mullya.app.service.mapper;

import com.mullya.app.domain.DeliveryDetails;
import com.mullya.app.service.dto.DeliveryDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryDetails} and its DTO {@link DeliveryDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, OrderMapper.class, CancellationDetailsMapper.class })
public interface DeliveryDetailsMapper extends EntityMapper<DeliveryDetailsDTO, DeliveryDetails> {
    @Mapping(target = "fromAddress", source = "fromAddress", qualifiedByName = "id")
    @Mapping(target = "toAddress", source = "toAddress", qualifiedByName = "id")
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    @Mapping(target = "cancellation", source = "cancellation", qualifiedByName = "id")
    DeliveryDetailsDTO toDto(DeliveryDetails s);
}
