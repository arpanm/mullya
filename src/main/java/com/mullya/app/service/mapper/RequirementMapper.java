package com.mullya.app.service.mapper;

import com.mullya.app.domain.Requirement;
import com.mullya.app.service.dto.RequirementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requirement} and its DTO {@link RequirementDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, ActorMapper.class })
public interface RequirementMapper extends EntityMapper<RequirementDTO, Requirement> {
    @Mapping(target = "buyerAddress", source = "buyerAddress", qualifiedByName = "id")
    @Mapping(target = "farmerAddress", source = "farmerAddress", qualifiedByName = "id")
    @Mapping(target = "buyerActor", source = "buyerActor", qualifiedByName = "id")
    @Mapping(target = "acceptedAgentActor", source = "acceptedAgentActor", qualifiedByName = "id")
    @Mapping(target = "farmerActor", source = "farmerActor", qualifiedByName = "id")
    RequirementDTO toDto(Requirement s);
}
