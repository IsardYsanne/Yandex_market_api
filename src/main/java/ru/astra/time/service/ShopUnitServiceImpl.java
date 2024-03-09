package ru.astra.time.service;

import org.springframework.stereotype.Service;
import ru.astra.time.enums.ShopUnitType;
import ru.astra.time.mapper.ShopUnitMapper;
import ru.astra.time.model.ShopUnitImportRequest;
import ru.astra.time.model.ShopUnitStatisticResponse;
import ru.astra.time.model.entity.ShopUnit;
import ru.astra.time.model.entity.ShopUnitStatisticUnit;
import ru.astra.time.repository.ShopUnitRepository;
import ru.astra.time.repository.StatisticRepository;
import ru.astra.time.util.PriceHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для Продукта.
 */
@Service
public class ShopUnitServiceImpl implements ShopUnitService {

    /**
     * Репозиторий продукта.
     */
    private ShopUnitRepository shopUnitRepository;

    /**
     * Маппер продукта.
     */
    private ShopUnitMapper shopUnitMapper;

    /**
     * Репозиторий статистики продукта.
     */
    private StatisticRepository statisticRepository;

    /**
     * Обработчик цены.
     */
    private PriceHandler priceHandler;

    public ShopUnitServiceImpl(ShopUnitRepository shopUnitRepository,
                               ShopUnitMapper shopUnitMapper,
                               StatisticRepository statisticRepository,
                               PriceHandler priceHandler) {
        this.shopUnitRepository = shopUnitRepository;
        this.shopUnitMapper = shopUnitMapper;
        this.statisticRepository = statisticRepository;
        this.priceHandler = priceHandler;
    }

    /**
     * Найти информацию о продукте по идентификатору.
     *
     * @param uuid идентификатор продукта.
     * @return информация о продукте (сущность).
     */
    @Override
    public ShopUnit findProductById(final UUID uuid) {
        final ShopUnit product = shopUnitRepository.findById(uuid).orElse(null);
        if (product.getType().equals(ShopUnitType.OFFER)) {
            product.setChildren(null);
        }
        return product;
    }

    /**
     * Удалить продукт по идентификатору вместе с его статистикой.
     *
     * @param uuid идентификатор продукта.
     */
    @Override
    public void deleteProductById(final UUID uuid) {
        final ShopUnit product = shopUnitRepository.findById(uuid).orElseThrow();
        final List<ShopUnit> childrenOfProduct = product.getChildren();
        final UUID productId = product.getId();
        if (childrenOfProduct != null) {
            for (ShopUnit child : childrenOfProduct) {
                final UUID childId = child.getId();
                if (child.getChildren() != null) {
                    for (ShopUnit childOfChild : child.getChildren()) {
                        statisticRepository.deleteShopUnitStatisticUnitByShopUnitId(childOfChild.getId());
                        shopUnitRepository.deleteById(childOfChild.getId());
                    }
                }
                statisticRepository.deleteShopUnitStatisticUnitByShopUnitId(childId);
                shopUnitRepository.deleteById(childId);
            }
        }
        statisticRepository.deleteShopUnitStatisticUnitByShopUnitId(productId);
        shopUnitRepository.deleteById(productId);
    }

    /**
     * Создает/обновляет продукт типа category/offer.
     *
     * @param importRequest данные, которые прилетели из запроса.
     */
    @Override
    public void importProducts(final ShopUnitImportRequest importRequest) {
        final LocalDateTime date = importRequest.getUpdateDate();
        final List<ShopUnit> products = shopUnitMapper.toShopUnits(importRequest, date);
        for (ShopUnit product : products) {
            product.setDate(date);

            if (product.getId().equals(product.getParentId())) {
                throw new RuntimeException("Invalid data");
            }
            if ((product.getType().equals(ShopUnitType.OFFER)) && (product.getPrice() < 0 || product.getPrice() == null)) {
                throw new RuntimeException("Invalid data");
            }
            if (product.getType().equals(ShopUnitType.CATEGORY) && product.getPrice() != null) {
                throw new RuntimeException("Invalid data");
            }

            if (product.getParentId() != null) {
                final Optional<ShopUnit> parent = findShopUnitParent(product);
                shopUnitRepository.save(parent.get());

                if (parent.get().getType().equals(ShopUnitType.OFFER)) {
                    throw new RuntimeException("Invalid data");
                }
                if (Objects.equals(parent.get().getParentId(), product.getId())) {
                    throw new RuntimeException("Invalid data");
                }
            }
            shopUnitRepository.saveAll(products);

            final ShopUnitStatisticUnit shopUnitStatisticUnit = new ShopUnitStatisticUnit(product);
            priceHandler.setStatisticPrice(shopUnitStatisticUnit, product);
        }
    }

    /**
     * Найти сущность категории-родителя.
     *
     * @param product родитель.
     * @return сущность категории-родителя.
     */
    public Optional<ShopUnit> findShopUnitParent(final ShopUnit product) {
        final UUID parentId = product.getParentId();
        return shopUnitRepository.findById(parentId);
    }

    /**
     * Найти всех потомков продукта по его идентификатору.
     *
     * @param uuid идентификатор продукта.
     * @return список потомков.
     */
    @Override
    public List<ShopUnit> findAllChildrenById(final UUID uuid) {
        final ShopUnit product = shopUnitRepository.findById(uuid).orElseThrow();
        return product.getChildren();
    }

    /**
     * Получить список товаров, цена которых была обновлена за последние 24 часа включительно.
     *
     * @param date дата, переданная в параметры.
     * @return ответ-статистика.
     */
    @Override
    public ShopUnitStatisticResponse findAllSales(final LocalDateTime date) {
        LocalDateTime dateMinusDay = date.minusDays(1);
        List<ShopUnitStatisticUnit> sales = statisticRepository.findByTypeAndDateIsBetween(ShopUnitType.OFFER, dateMinusDay, date);
        return new ShopUnitStatisticResponse(sales);
    }

    /**
     * Получить статистику (историю обновлений) по товару/категории за заданный полуинтервал [from, to).
     *
     * @param uuid     идентификатор.
     * @param dateFrom начиная с даты.
     * @param dateTo   по дату, не включительно.
     * @return ответ-статистика за заданный полуинтервал [from, to).
     */
    @Override
    public ShopUnitStatisticResponse findNodeStatisticForDatesInterval(UUID uuid, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<ShopUnitStatisticUnit> sales = statisticRepository.findAllByIdEqualsAndDateGreaterThanAndDateLessThan(uuid, dateFrom, dateTo);
        for (ShopUnitStatisticUnit statisticUnit : sales) {
            final ShopUnit shopUnit = shopUnitRepository.findById(statisticUnit.getShopUnitId()).orElseThrow();

            int sumPrice = 0;
            int productCount = 0;

            if (shopUnit.getType().equals(ShopUnitType.OFFER)) {
                statisticUnit.setProductCount(1);
                statisticUnit.setSumPrice(statisticUnit.getPrice());
            } else if (shopUnit.getType().equals(ShopUnitType.CATEGORY)) {
                if (shopUnit.getChildren() != null) {
                    for (ShopUnit child : shopUnit.getChildren()) {
                        final ShopUnitStatisticUnit childStatisticUnit = new ShopUnitStatisticUnit(child);
                        sumPrice = sumPrice + child.getPrice();
                        productCount = productCount + 1;
                        childStatisticUnit.setSumPrice(sumPrice);
                        childStatisticUnit.setProductCount(productCount);
                        int result = sumPrice / productCount;
                        statisticUnit.setPrice(result);
                    }
                }
                statisticRepository.save(statisticUnit);
            }
        }
        return new ShopUnitStatisticResponse(sales);
    }
}
