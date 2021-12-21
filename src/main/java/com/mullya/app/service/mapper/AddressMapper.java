package com.mullya.app.service.mapper;

import com.mullya.app.domain.Address;
import com.mullya.app.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = { HubMapper.class, UserMapper.class })
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "hub", source = "hub", qualifiedByName = "id")
    @Mapping(target = "user", source = "user", qualifiedByName = "id")
    AddressDTO toDto(Address s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoId(Address address);
}
