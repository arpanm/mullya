package com.mullya.app.service.mapper;

import com.mullya.app.domain.Stock;
import com.mullya.app.service.dto.StockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stock} and its DTO {@link StockDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, UserMapper.class, CatalogueMapper.class })
public interface StockMapper extends EntityMapper<StockDTO, Stock> {
    @Mapping(target = "farmerAddress", source = "farmerAddress", qualifiedByName = "id")
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "id")
    @Mapping(target = "category", source = "category", qualifiedByName = "id")
    @Mapping(target = "variant", source = "variant", qualifiedByName = "id")
    @Mapping(target = "subVariant", source = "subVariant", qualifiedByName = "id")
    StockDTO toDto(Stock s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StockDTO toDtoId(Stock stock);
}
