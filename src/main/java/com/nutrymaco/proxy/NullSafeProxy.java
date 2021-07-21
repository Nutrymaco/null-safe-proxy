package com.nutrymaco.proxy;

import java.util.Arrays;
import java.util.List;

public class NullSafeProxy<T> {

    private final T object;
    private final List<Class<? extends T>> interfaces;

    private NullSafeProxy(T object, List<Class<? extends T>> interfaces) {
        this.object = object;
        this.interfaces = interfaces;
    }

    public static <T> ProxyBuilder<T> of(T object) {
        return new ProxyBuilder<>(object);
    }

    public T create() {

        return object;
    }

    public static class ProxyBuilder<T> {

        private final T object;

        private ProxyBuilder(T object) {
            this.object = object;
        }

        public NullSafeProxy<T> withInterfaces(Class<? extends T> ... interfaces) {
            return new NullSafeProxy<T>(object, Arrays.asList(interfaces));
        }
    }

}
