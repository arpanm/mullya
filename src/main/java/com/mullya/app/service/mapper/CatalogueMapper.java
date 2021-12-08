package com.mullya.app.service.mapper;

import com.mullya.app.domain.Catalogue;
import com.mullya.app.service.dto.CatalogueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Catalogue} and its DTO {@link CatalogueDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogueMapper extends EntityMapper<CatalogueDTO, Catalogue> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "id")
    CatalogueDTO toDto(Catalogue s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CatalogueDTO toDtoId(Catalogue catalogue);
}
