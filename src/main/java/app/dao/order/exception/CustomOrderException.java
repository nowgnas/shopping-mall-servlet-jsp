package app.dao.order.exception;

import app.error.ErrorCode;

public class CustomOrderException extends RuntimeException {
    public CustomOrderException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
