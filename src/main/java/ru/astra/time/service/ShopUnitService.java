package ru.astra.time.service;

import ru.astra.time.model.ShopUnitImportRequest;
import ru.astra.time.model.ShopUnitStatisticResponse;
import ru.astra.time.model.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Сервис для Продукта.
 */
public interface ShopUnitService {

    void deleteProductById(final UUID uuid);

    ShopUnit findProductById(final UUID uuid);

    List<ShopUnit> findAllChildrenById(final UUID uuid);

    void importProducts(final ShopUnitImportRequest importRequest);

    ShopUnitStatisticResponse findAllSales(final LocalDateTime date);

    ShopUnitStatisticResponse findNodeStatisticForDatesInterval(final UUID uuid,
                                                                final LocalDateTime dateFrom,
                                                                final LocalDateTime  dateTo);
}
