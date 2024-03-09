package ru.astra.time.model;

import ru.astra.time.model.entity.ShopUnitStatisticUnit;

import java.util.List;

/**
 * То, что получим в ответе.
 */
public class ShopUnitStatisticResponse {

    /**
     * История в произвольном порядке.
     */
    private List<ShopUnitStatisticUnit> statistics;

    public ShopUnitStatisticResponse(List<ShopUnitStatisticUnit> statistics) {
        this.statistics = statistics;
    }

    public List<ShopUnitStatisticUnit> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<ShopUnitStatisticUnit> statistics) {
        this.statistics = statistics;
    }
}
