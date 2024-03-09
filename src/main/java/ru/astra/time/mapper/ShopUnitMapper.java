package ru.astra.time.mapper;

import ru.astra.time.model.ShopUnitImportRequest;
import ru.astra.time.model.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Маппер для Продукта.
 */
public interface ShopUnitMapper {

    List<ShopUnit> toShopUnits(final ShopUnitImportRequest importRequest, final LocalDateTime updateDate);
}
