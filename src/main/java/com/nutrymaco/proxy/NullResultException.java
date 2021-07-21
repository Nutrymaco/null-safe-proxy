package com.nutrymaco.proxy;

import java.lang.reflect.Method;

public class NullResultException extends RuntimeException {

    private final Method method;

    public NullResultException(Method method) {
        this.method = method;
    }

    @Override public String getMessage() {
        return "method %s return null".formatted(method);
    }

    @Override public boolean equals(Object o) {
        if (o instanceof NullResultException nre) {
            return method.equals(nre.method);
        }
        return false;
    }
}
