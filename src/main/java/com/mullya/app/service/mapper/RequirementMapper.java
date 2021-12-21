package com.mullya.app.service.mapper;

import com.mullya.app.domain.Requirement;
import com.mullya.app.service.dto.RequirementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Requirement} and its DTO {@link RequirementDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, UserMapper.class, CatalogueMapper.class })
public interface RequirementMapper extends EntityMapper<RequirementDTO, Requirement> {
    @Mapping(target = "buyerAddress", source = "buyerAddress", qualifiedByName = "id")
    @Mapping(target = "buyerUser", source = "buyerUser", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    @Mapping(target = "variant", source = "variant", qualifiedByName = "id")
    @Mapping(target = "subVariant", source = "subVariant", qualifiedByName = "id")
    RequirementDTO toDto(Requirement s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RequirementDTO toDtoId(Requirement requirement);
}
