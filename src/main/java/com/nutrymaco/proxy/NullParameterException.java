package com.nutrymaco.proxy;

import java.lang.reflect.Parameter;

public class NullParameterException extends RuntimeException {

    private final Parameter parameter;

    public NullParameterException(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override public String getMessage() {
        return "passing null in parameter : %s".formatted(parameter);
    }

    @Override public boolean equals(Object o) {
        if (o instanceof NullParameterException npe) {
            return this.parameter.equals(npe.parameter);
        }
        return false;
    }
}
