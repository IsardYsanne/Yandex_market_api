package ru.astra.time.mapper;

import org.springframework.stereotype.Component;
import ru.astra.time.dto.ShopUnitImport;
import ru.astra.time.enums.ShopUnitType;
import ru.astra.time.model.ShopUnitImportRequest;
import ru.astra.time.model.entity.ShopUnit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Маппер для Продукта.
 */
@Component
public class ShopUnitMapperImpl implements ShopUnitMapper {

    /**
     * Преобразует полученные данные в список Продуктов.
     *
     * @param importRequest тело запроса.
     * @param updateDate    дата обновления.
     * @return список Продуктов.
     */
    @Override
    public List<ShopUnit> toShopUnits(final ShopUnitImportRequest importRequest, final LocalDateTime updateDate) {
        final List<ShopUnit> products = new ArrayList<>();
        final List<ShopUnitImport> shopUnitImports = importRequest.getProducts();

        for (ShopUnitImport shopUnitImport : shopUnitImports) {
            final ShopUnit product = new ShopUnit(
                    shopUnitImport.getId(),
                    shopUnitImport.getName(),
                    shopUnitImport.getPrice(),
                    updateDate,
                    null,
                    shopUnitImport.getType(),
                    shopUnitImport.getParentId()
            );

            if (product.getId() == null || product.getName() == null || product.getType() == null || product.getDate() == null) {
                throw new RuntimeException("Invalid data");
            }

            if (product.getType().equals(ShopUnitType.CATEGORY)) {
                List<ShopUnit> productsChildren = new ArrayList<>();
                product.setChildren(productsChildren);
                products.add(product);
            } else if (product.getType().equals(ShopUnitType.OFFER)) {
                product.setChildren(null);
                products.add(product);
            }
        }
        return products;
    }
}
