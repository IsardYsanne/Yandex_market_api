package ru.astra.time.model;

import ru.astra.time.dto.ShopUnitImport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Тело запроса для импорта новых/повторно импортируемых продуктов.
 */
public class ShopUnitImportRequest {

    /**
     * Время обновления добавляемых товаров/категорий.
     */
    private LocalDateTime updateDate;

    /**
     * Список импортируемых продуктов.
     */
    private List<ShopUnitImport> products;

    public ShopUnitImportRequest(LocalDateTime updateDate, List<ShopUnitImport> products) {
        this.updateDate = updateDate;
        this.products = products;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public List<ShopUnitImport> getProducts() {
        return products;
    }

    public void setProducts(List<ShopUnitImport> products) {
        this.products = products;
    }
}
