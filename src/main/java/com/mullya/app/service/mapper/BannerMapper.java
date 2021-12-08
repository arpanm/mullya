package com.mullya.app.service.mapper;

import com.mullya.app.domain.Banner;
import com.mullya.app.service.dto.BannerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banner} and its DTO {@link BannerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BannerMapper extends EntityMapper<BannerDTO, Banner> {}
