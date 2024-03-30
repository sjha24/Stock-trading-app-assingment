package com.CareBuddy.stock.trading.application.exception;

public class InvalidTokenException extends Throwable {
    public InvalidTokenException(String pleaseEnterValidToken) {
        super(pleaseEnterValidToken);
    }
}
