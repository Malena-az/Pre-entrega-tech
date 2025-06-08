package com.calyma.excepciones;

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String message) {
        super(message);
    }
}
