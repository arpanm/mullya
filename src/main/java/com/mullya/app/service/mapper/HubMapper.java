package com.mullya.app.service.mapper;

import com.mullya.app.domain.Hub;
import com.mullya.app.service.dto.HubDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hub} and its DTO {@link HubDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HubMapper extends EntityMapper<HubDTO, Hub> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HubDTO toDtoId(Hub hub);
}
