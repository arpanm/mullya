package com.mullya.app.service.mapper;

import com.mullya.app.domain.Order;
import com.mullya.app.service.dto.OrderDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { RemittanceDetailsMapper.class, RequirementMapper.class, BidsMapper.class, UserMapper.class, StockMapper.class }
)
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mapping(target = "remittances", source = "remittances", qualifiedByName = "idSet")
    @Mapping(target = "requirement", source = "requirement", qualifiedByName = "id")
    @Mapping(target = "bid", source = "bid", qualifiedByName = "id")
    @Mapping(target = "assignedAgent", source = "assignedAgent", qualifiedByName = "id")
    @Mapping(target = "stock", source = "stock", qualifiedByName = "id")
    OrderDTO toDto(Order s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoId(Order order);

    @Mapping(target = "removeRemittance", ignore = true)
    Order toEntity(OrderDTO orderDTO);
}
