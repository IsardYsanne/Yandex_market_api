package ru.astra.time;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Приложение, REST API сервис, который позволяет магазинам загружать и обновлять информацию о товарах,
 * а пользователям - смотреть какие товары были обновлены за последние сутки.
 *
 * @author GKordyukova
 */
@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
