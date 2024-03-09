package ru.astra.time.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.astra.time.enums.ShopUnitType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность Продукта.
 */
@Entity
@Table(name = "product")
public class ShopUnit {

    /**
     * Уникальный идентификатор.
     */
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * Имя категории.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Цена.
     */
    @Column(name = "price")
    private Integer price;

    /**
     * Дата последнего обновления элемента.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    /**
     * UUID родительской категории.
     */
    @Column(name = "parent_id")
    private UUID parentId;

    /**
     * Тип элемента: категория или товар.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ShopUnitType type;

    /**
     * Список всех дочерних товаров/категорий. Для товаров поле равно null.
     */
    @OneToMany(mappedBy = "parentId", cascade = {CascadeType.ALL})
    private List<ShopUnit> children = new ArrayList<>();

    public ShopUnit() {
    }

    public ShopUnit(UUID id, String name, Integer price, LocalDateTime date, List<ShopUnit> children, ShopUnitType type, UUID parentId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.children = children;
        this.type = type;
        this.parentId = parentId;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(List<ShopUnit> children) {
        this.children = children;
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
}
