package com.mullya.app.service.mapper;

import com.mullya.app.domain.Requirement;
import com.mullya.app.service.dto.RequirementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requirement} and its DTO {@link RequirementDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, ActorMapper.class })
public interface RequirementMapper extends EntityMapper<RequirementDTO, Requirement> {
    @Mapping(target = "address", source = "address", qualifiedByName = "id")
    @Mapping(target = "actor", source = "actor", qualifiedByName = "id")
    RequirementDTO toDto(Requirement s);
}
