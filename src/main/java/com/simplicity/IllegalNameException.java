package com.simplicity;

public class IllegalNameException extends java.lang.Exception {
    private String name;

    public IllegalNameException(String name) {
        this.name = name;
    }

    public String getMessage() {
        if (name == null) {
            return "Nama tidak boleh kosong";
        } else {
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c)) {
                    return "Nama tidak boleh mengandung angka atau simbol";
                }
            }
            if (name.length() < 4) {
                return "Nama harus lebih dari 4 karakter";
            } else if (name.length() > 10) {
                return "Nama harus kurang dari 10 karakter";
            } else {
                return "Nama tidak valid";
            }
        }
    }
}
