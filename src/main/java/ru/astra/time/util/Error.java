package ru.astra.time.util;

import org.springframework.http.HttpStatus;

/**
 * Класс ошибки, которая вернется при неудачном запросе.
 */
public class Error {

    /**
     * Код ошибки.
     */
    public Integer code;

    /**
     * Сообщение.
     */
    public String message;

    public Error(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
