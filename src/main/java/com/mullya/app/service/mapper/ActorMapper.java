package com.mullya.app.service.mapper;

import com.mullya.app.domain.Actor;
import com.mullya.app.service.dto.ActorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Actor} and its DTO {@link ActorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActorMapper extends EntityMapper<ActorDTO, Actor> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ActorDTO toDtoId(Actor actor);
}
