package ru.astra.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astra.time.enums.ShopUnitType;
import ru.astra.time.model.entity.ShopUnitStatisticUnit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для статистики.
 */
public interface StatisticRepository extends JpaRepository<ShopUnitStatisticUnit, UUID> {

    @Transactional
    void deleteShopUnitStatisticUnitByParentId(final UUID parentId);

    @Transactional
    void deleteShopUnitStatisticUnitByShopUnitId(final UUID productId);

    @Transactional
    void deleteAllByShopUnitId(final UUID productId);

    List<ShopUnitStatisticUnit> findByTypeAndDateIsBetween(final ShopUnitType shopUnitType,
                                                           final LocalDateTime dateMinusDay,
                                                           final LocalDateTime date);

    List<ShopUnitStatisticUnit> findAllByIdEqualsAndDateGreaterThanAndDateLessThan(final UUID uuid,
                                                                                   final LocalDateTime dateFrom,
                                                                                   final LocalDateTime dateTo);
}
