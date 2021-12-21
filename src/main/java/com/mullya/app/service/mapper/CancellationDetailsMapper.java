package com.mullya.app.service.mapper;

import com.mullya.app.domain.CancellationDetails;
import com.mullya.app.service.dto.CancellationDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CancellationDetails} and its DTO {@link CancellationDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class, UserMapper.class })
public interface CancellationDetailsMapper extends EntityMapper<CancellationDetailsDTO, CancellationDetails> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    @Mapping(target = "approver", source = "approver", qualifiedByName = "id")
    @Mapping(target = "initiator", source = "initiator", qualifiedByName = "id")
    CancellationDetailsDTO toDto(CancellationDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CancellationDetailsDTO toDtoId(CancellationDetails cancellationDetails);
}
