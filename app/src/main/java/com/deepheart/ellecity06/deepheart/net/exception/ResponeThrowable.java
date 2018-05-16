package com.deepheart.ellecity06.deepheart.net.exception;

public class ResponeThrowable extends Exception {
    public int code;
    public String message;

    public ResponeThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
