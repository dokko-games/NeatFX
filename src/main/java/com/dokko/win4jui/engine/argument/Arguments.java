package com.dokko.win4jui.engine.argument;

import com.dokko.win4jui.core.error.Error;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ToString
public class Arguments {
    private final Map<String, Argument<?>> arguments = new HashMap<>();
    private final Map<String, Argument<?>> requiredArguments = new HashMap<>(); // For tracking required arguments
    private final Set<String> parsedArguments = new HashSet<>(); // Track parsed arguments

    // Accepts an optional argument
    public void accept(String name) {
        arguments.put(name, new Argument<>(name, false, null));
    }

    // Accepts an optional argument with a specific type
    public <T> void accept(String name, Class<T> type) {
        if(type.equals(byte.class)){
            accept(name, Byte.class);
            return;
        } else if(type.equals(short.class)){
            accept(name, Short.class);
            return;
        }else if(type.equals(int.class)){
            accept(name, Integer.class);
            return;
        }else if(type.equals(long.class)){
            accept(name, Long.class);
            return;
        }else if(type.equals(float.class)){
            accept(name, Float.class);
            return;
        }else if(type.equals(double.class)){
            accept(name, Double.class);
            return;
        } else if(type.equals(boolean.class)){
            accept(name, Boolean.class);
            return;
        }
        arguments.put(name, new Argument<>(name, true, type));
    }

    // Requires an argument with a specific type
    public <T> void require(String name, Class<T> type) {
        if(type.equals(byte.class)){
            require(name, Byte.class);
            return;
        } else if(type.equals(short.class)){
            require(name, Short.class);
            return;
        }else if(type.equals(int.class)){
            require(name, Integer.class);
            return;
        }else if(type.equals(long.class)){
            require(name, Long.class);
            return;
        }else if(type.equals(float.class)){
            require(name, Float.class);
            return;
        }else if(type.equals(double.class)){
            require(name, Double.class);
            return;
        } else if(type.equals(boolean.class)){
            require(name, Boolean.class);
            return;
        }
        Argument<T> argument = new Argument<>(name, true, type);
        arguments.put(name, argument);
        requiredArguments.put(name, argument); // Track this as a required argument
    }

    // Parse the arguments from an array of strings (simulating command line args)
    public void parse(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String argName = args[i];
            if (argName.startsWith("--")) {
                argName = argName.substring(2); // Remove the leading '--'
                Argument<?> argument = arguments.get(argName);
                if (argument == null) {
                    throw Error.from(new IllegalArgumentException("Unknown argument: " + argName)).getException();
                }

                parsedArguments.add(argName); // Mark this argument as parsed

                if (argument.hasValue()) {
                    // If it expects a value, ensure the next argument is available and set the value
                    if (i + 1 < args.length) {
                        String valueStr = args[i + 1];
                        setArgumentValue(argument, valueStr);
                        i++; // Skip the next argument since it was the value for this one
                    } else {
                        throw Error.from(new IllegalArgumentException("Argument --" + argName + " requires a value.")).getException();
                    }
                }
            }
        }

        // Check for required arguments that were not provided
        for (Argument<?> requiredArg : requiredArguments.values()) {
            if (requiredArg.hasValue() && requiredArg.getValue() == null) {
                throw Error.from(new IllegalArgumentException("Missing required argument: --" + requiredArg.getName())).getException();
            }
        }
    }

    private void setArgumentValue(Argument<?> argument, String valueStr) {
        // Use the argument type to properly cast the value to the correct type
        if (argument.getType() == String.class) {
            argument.setValue(valueStr);
        } else if (argument.getType() == Byte.class) {
            argument.setValue(Byte.valueOf(valueStr));
        } else if (argument.getType() == Short.class) {
            argument.setValue(Short.valueOf(valueStr));
        } else if (argument.getType() == Integer.class) {
            argument.setValue(Integer.valueOf(valueStr));
        } else if (argument.getType() == Long.class) {
            argument.setValue(Long.valueOf(valueStr));
        } else if (argument.getType() == Float.class) {
            argument.setValue(Float.valueOf(valueStr));
        } else if (argument.getType() == Double.class) {
            argument.setValue(Double.valueOf(valueStr));
        } else if (argument.getType() == Boolean.class) {
            argument.setValue(Boolean.valueOf(valueStr));
        } else {
            throw Error.from(new IllegalArgumentException("Unsupported argument type: " + argument.getType().getName())).getException();
        }
    }

    // Check if an argument has been parsed (i.e., passed in the command line args)
    public boolean hasArgument(String name) {
        return parsedArguments.contains(name);
    }

    // Get the value of an argument
    public <T> T getArgumentValue(String name, Class<T> cls) {
        Argument<T> argument = (Argument<T>) arguments.get(name);
        if (argument == null) {
            throw new IllegalArgumentException("Argument not found: " + name);
        }
        return argument.getValue();
    }
}
