package ru.astra.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astra.time.model.entity.ShopUnit;

import java.util.UUID;

/**
 * Репозиторий для продукта.
 */
public interface ShopUnitRepository extends JpaRepository<ShopUnit, UUID>  {
}
