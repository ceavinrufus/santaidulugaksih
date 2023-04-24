package com.simplicity;

import java.util.*;

public class Peta<T> {
    // Kalo col maksudnya sumbu X (karena col arahnya horizontal)
    // Kalo row maksudnya sumbu Y (karena row arahnya vertikal)
    private ArrayList<ArrayList<T>> matriks;

    public Peta(int column, int row) {
        matriks = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            ArrayList<T> rowList = new ArrayList<>(column);
            for (int j = 0; j < column; j++) {
                rowList.add(null);
            }
            matriks.add(rowList);
        }
    }

    // colIndex = sumbu X, rowIndex = sumbu Y
    public void setElement(int colIndex, int rowIndex, T element) {
        if (rowIndex < 0 || rowIndex >= matriks.size() || colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        matriks.get(rowIndex).set(colIndex, element);
    }

    public T getElement(int colIndex, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= matriks.size() || colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        return matriks.get(rowIndex).get(colIndex);
    }

    public void addBottom() {
        ArrayList<T> newRow = new ArrayList<T>(getColumn());
        for (int i = 0; i < getColumn(); i++) {
            newRow.add(null);
        }
        matriks.add(newRow);
    }

    public void addTop() {
        ArrayList<T> newRow = new ArrayList<T>(getColumn());
        for (int i = 0; i < getColumn(); i++) {
            newRow.add(null);
        }
        matriks.add(0, newRow);
    }

    public void addRight() {
        for (ArrayList<T> row : matriks) {
            row.add(null);
        }
    }

    public void addLeft() {
        for (ArrayList<T> row : matriks) {
            row.add(0, null);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= matriks.size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        matriks.remove(rowIndex);
    }

    public void removeColumn(int colIndex) {
        if (colIndex < 0 || colIndex >= matriks.get(0).size()) {
            throw new IndexOutOfBoundsException("Invalid matrix index");
        }
        for (ArrayList<T> row : matriks) {
            row.remove(colIndex);
        }
    }

    public int getRow() {
        return matriks.size();
    }

    public int getColumn() {
        return matriks.isEmpty() ? 0 : matriks.get(0).size();
    }
}
