package ru.astra.time.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import ru.astra.time.model.ShopUnitImportRequest;
import ru.astra.time.service.ShopUnitService;
import ru.astra.time.util.Error;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Контроллер для работы с сущностью Продукта.
 */
@RestController
@RequestMapping("/")
public class ShopUnitController {

    /**
     * Сервис продукта.
     */
    private ShopUnitService shopUnitService;

    public ShopUnitController(ShopUnitService shopUnitService) {
        this.shopUnitService = shopUnitService;
    }

    @PostMapping("imports")
    public ResponseEntity<?> importUnits(@RequestBody ShopUnitImportRequest importRequestProduct) {
        try {
            shopUnitService.importProducts(importRequestProduct);
            return ResponseEntity.ok("Вставка или обновление прошли успешно.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Error(HttpStatus.BAD_REQUEST, "Невалидная схема документа или входные данные не верны."));
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {
        try {
            shopUnitService.deleteProductById(id);
            return ResponseEntity.ok("Удаление прошло успешно.");
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(404).body(new Error(HttpStatus.NOT_FOUND, "Категория/товар не найден."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Error(HttpStatus.BAD_REQUEST, "Невалидная схема документа или входные данные не верны."));
        }
    }

    @GetMapping("nodes/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") UUID id) {
        try {
            return ResponseEntity.ok(shopUnitService.findProductById(id));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(404).body(new Error(HttpStatus.NOT_FOUND, "Категория/товар не найден."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Error(HttpStatus.BAD_REQUEST, "Невалидная схема документа или входные данные не верны."));
        }
    }

    @GetMapping("sales")
    public ResponseEntity<?> findAllSales(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        try {
            return ResponseEntity.ok(shopUnitService.findAllSales(date));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Error(HttpStatus.BAD_REQUEST, "Невалидная схема документа или входные данные не верны."));
        }
    }

    @GetMapping("node/{id}/statistic")
    public ResponseEntity<?> findNodeStatistic(@PathVariable(value = "id") UUID id,
                                               @RequestParam(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
                                               @RequestParam(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo) {
        try {
            return ResponseEntity.ok(shopUnitService.findNodeStatisticForDatesInterval(id, dateFrom, dateTo));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(404).body(new Error(HttpStatus.NOT_FOUND, "Категория/товар не найден"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Error(HttpStatus.BAD_REQUEST, "Некорректный формат запроса или некорректные даты интервала."));
        }
    }
}
