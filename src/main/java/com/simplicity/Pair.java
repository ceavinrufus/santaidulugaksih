package com.simplicity;
import java.util.Objects;

public class Pair<T,E> {
    private T key;
    private E value;

    public Pair(T k , E v)
    {
        setKey(k);
        setValue(v);
    }

    public T getKey() {
        return key;
    }
    
    public void setKey(T key) {
        this.key = key;
    }

    public E getValue() {
        return value;
    }
    
    public void setValue(E value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair<?, ?> other = (Pair<?, ?>) obj;

        return Objects.equals(key, other.key)
                && Objects.equals(value, other.value);
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}