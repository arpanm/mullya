package com.mullya.app.service.mapper;

import com.mullya.app.domain.BiddingDetails;
import com.mullya.app.service.dto.BiddingDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BiddingDetails} and its DTO {@link BiddingDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { StockMapper.class })
public interface BiddingDetailsMapper extends EntityMapper<BiddingDetailsDTO, BiddingDetails> {
    @Mapping(target = "stock", source = "stock", qualifiedByName = "id")
    BiddingDetailsDTO toDto(BiddingDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BiddingDetailsDTO toDtoId(BiddingDetails biddingDetails);
}
