package com.dokko.win4jui.engine.argument;

import com.dokko.win4jui.core.error.Error;
import lombok.Getter;

public class Argument<T> {
    @Getter
    private final String name;
    private final boolean hasValue;
    @Getter
    private final Class<T> type;
    @Getter
    private T value;

    public Argument(String name, boolean hasValue, Class<T> type) {
        this.name = name;
        this.hasValue = hasValue;
        this.type = type;
        if (hasValue) {
            // Initialize the value with a default value based on the type
            if (type == String.class) {
                this.value = type.cast(""); // Default to empty string
            } else if (type == Integer.class) {
                this.value = type.cast(0); // Default to 0 for Integer
            } else if (type == Float.class) {
                this.value = type.cast(0); // Default to 0 for Float
            } else if (type == Double.class) {
                this.value = type.cast(0); // Default to 0 for Double
            } else if (type == Long.class) {
                this.value = type.cast(0); // Default to 0 for Long
            } else if (type == Short.class) {
                this.value = type.cast(0); // Default to 0 for Short
            } else if (type == Byte.class) {
                this.value = type.cast(0); // Default to 0 for Byte
            } else if (type == Boolean.class) {
                this.value = type.cast(false); // Default to false for Boolean
            } else {
                try {
                    this.value = type.getDeclaredConstructor().newInstance(); // Initialize default value
                } catch (Exception e) {
                    throw Error.from(e).getException();
                }
            }
        }
    }

    public boolean hasValue() {
        return hasValue;
    }

    public void setValue(Object value) {
        if(value.getClass() != type){
            throw Error.from(new IllegalArgumentException("Value is not the same type as the argument ("+name+")")).getException();
        }
        if (hasValue) {
            this.value = (T)value;
        }
    }

    @Override
    public String toString() {
        return "Argument{name='" + name + "', hasValue=" + hasValue + ", value=" + value + '}';
    }
}
