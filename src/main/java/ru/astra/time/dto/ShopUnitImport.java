package ru.astra.time.dto;

import ru.astra.time.enums.ShopUnitType;

import java.util.UUID;

/**
 * То, что будем импортировать в POST-запросе.
 */
public class ShopUnitImport {

    /**
     * Уникальный идентификатор.
     */
    private UUID id;

    /**
     * Имя элемента.
     */
    private String name;

    /**
     * Цена.
     */
    private Integer price;

    /**
     * UUID родительской категории.
     */
    private UUID parentId;

    /**
     * Тип элемента.
     */
    private ShopUnitType type;

    public ShopUnitImport(UUID id, String name, Integer price, UUID parentId, ShopUnitType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.parentId = parentId;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }
}
