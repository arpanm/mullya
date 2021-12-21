package com.mullya.app.service.mapper;

import com.mullya.app.domain.RemittanceDetails;
import com.mullya.app.service.dto.RemittanceDetailsDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RemittanceDetails} and its DTO {@link RemittanceDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface RemittanceDetailsMapper extends EntityMapper<RemittanceDetailsDTO, RemittanceDetails> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "id")
    RemittanceDetailsDTO toDto(RemittanceDetails s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RemittanceDetailsDTO> toDtoIdSet(Set<RemittanceDetails> remittanceDetails);
}
