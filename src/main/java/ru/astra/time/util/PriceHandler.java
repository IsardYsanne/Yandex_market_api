package ru.astra.time.util;

import org.springframework.stereotype.Component;
import ru.astra.time.enums.ShopUnitType;
import ru.astra.time.model.entity.ShopUnit;
import ru.astra.time.model.entity.ShopUnitStatisticUnit;
import ru.astra.time.repository.ShopUnitRepository;
import ru.astra.time.repository.StatisticRepository;

import java.util.UUID;

/**
 * Обработчик для цены категории.
 */
@Component
public class PriceHandler {

    /**
     * Репозиторий статистики по продукту.
     */
    private StatisticRepository statisticRepository;

    /**
     * Репозиторий продукта.
     */
    private ShopUnitRepository shopUnitRepository;

    public PriceHandler(StatisticRepository statisticRepository, ShopUnitRepository shopUnitRepository) {
        this.statisticRepository = statisticRepository;
        this.shopUnitRepository = shopUnitRepository;
    }

    /**
     * Задает цену продукту для статистики.
     *
     * @param shopUnitStatisticUnit статистика по продукту.
     * @param product               сам продукт.
     */
    public void setStatisticPrice(final ShopUnitStatisticUnit shopUnitStatisticUnit, final ShopUnit product) {
        int sumPrice = 0;
        int productCount = 0;

        if (product.getType().equals(ShopUnitType.OFFER)) {
            shopUnitStatisticUnit.setId(UUID.randomUUID());
            shopUnitStatisticUnit.setProductCount(1);
            shopUnitStatisticUnit.setSumPrice(shopUnitStatisticUnit.getPrice());
            statisticRepository.save(shopUnitStatisticUnit);
        } else if (product.getType().equals(ShopUnitType.CATEGORY)) {
            if (product.getChildren() != null) {
                for (ShopUnit child : product.getChildren()) {
                    final ShopUnitStatisticUnit childStatisticUnit = new ShopUnitStatisticUnit(child);
                    sumPrice = sumPrice + child.getPrice();
                    productCount = productCount + 1;
                    childStatisticUnit.setId(UUID.randomUUID());
                    childStatisticUnit.setSumPrice(sumPrice);
                    childStatisticUnit.setProductCount(productCount);
                    statisticRepository.save(childStatisticUnit);
                    int result = sumPrice / productCount;
                    product.setPrice(result);
                    shopUnitRepository.save(product);
                }
            }
            statisticRepository.save(shopUnitStatisticUnit);
        }
    }
}
