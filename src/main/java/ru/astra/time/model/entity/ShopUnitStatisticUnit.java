package ru.astra.time.model.entity;

import ru.astra.time.enums.ShopUnitType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность Истории обновлений.
 */
@Entity
@Table(name = "statistic")
public class ShopUnitStatisticUnit {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * UUID товара/категории, для которой будет отображаться статистика.
     */
    @Column(name = "product_id", nullable = false)
    private UUID shopUnitId;

    /**
     * Имя элемента.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Время последнего обновления элемента.
     */
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    /**
     * Тип элемента: категория или товар.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ShopUnitType type;

    /**
     * UUID родительской категории.
     */
    @Column(name = "parent_id")
    private UUID parentId;

    /**
     * Цена.
     */
    @Column(name = "price")
    private Integer price;

    /**
     * Сумма цен товаров.
     */
    @Column(name = "sum_price")
    private int sumPrice;

    /**
     * Количество товаров.
     */
    @Column(name = "product_count")
    private int productCount;

    public ShopUnitStatisticUnit() {}

    public ShopUnitStatisticUnit(ShopUnit shopUnit) {
        this.shopUnitId = shopUnit.getId();
        this.name = shopUnit.getName();
        this.date = shopUnit.getDate();
        this.type = shopUnit.getType();
        this.parentId = shopUnit.getParentId();
        this.price = shopUnit.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getShopUnitId() {
        return shopUnitId;
    }

    public void setShopUnitId(UUID shopUnitId) {
        this.shopUnitId = shopUnitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}
