package com.simplicity.ExceptionHandling;

public class IllegalNameException extends java.lang.Exception {
    String message;

    public IllegalNameException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("Wah, nama yang kamu masukkan tidak valid, nih!\n%s", message);
    }
}
