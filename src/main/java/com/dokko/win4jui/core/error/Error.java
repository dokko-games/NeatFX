package com.dokko.win4jui.core.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Simple class for error definition and processing. It contains a name, a message, a cause and a method to convert it into an Exception
 */
@Setter
@AllArgsConstructor
@Getter
public class Error {
    /**
     * The name of the error
     */
    private String name;
    /**
     * The message (data) of the error
     */
    private String message;
    /**
     * The cause of the error
     */
    private String cause;

    public Error(String name, String message){
        this(name, message, "unknown");
    }

    public Error(Exception e, String cause) {
        this(e.getClass().getSimpleName(), e.getMessage(), cause);
    }

    /**
     * Converts the error into an Exception
     * @return the error, as a RuntimeException
     */
    public RuntimeException getException() {
        return new RuntimeException(getName()+": "+getMessage()+".\nCause: "+getCause());
    }
}
