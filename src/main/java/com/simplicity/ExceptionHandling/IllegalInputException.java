package com.simplicity.ExceptionHandling;

public class IllegalInputException extends java.lang.Exception {
    String message;

    public IllegalInputException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return String.format("Wah, nama yang kamu masukkan tidak valid, nih!\n%s", message);
    }
}
