package com.tdtu.logistics_goods_service.exception.wrapper;


public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super();
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }
}
