package com.mullya.app.service.mapper;

import com.mullya.app.domain.Bids;
import com.mullya.app.service.dto.BidsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bids} and its DTO {@link BidsDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, BiddingDetailsMapper.class, ActorMapper.class })
public interface BidsMapper extends EntityMapper<BidsDTO, Bids> {
    @Mapping(target = "buyerAddress", source = "buyerAddress", qualifiedByName = "id")
    @Mapping(target = "biddingDetails", source = "biddingDetails", qualifiedByName = "id")
    @Mapping(target = "buyer", source = "buyer", qualifiedByName = "id")
    BidsDTO toDto(Bids s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BidsDTO toDtoId(Bids bids);
}
