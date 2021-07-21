package com.nutrymaco.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public <I extends T> I create() {
        return (I)Proxy.newProxyInstance(object.getClass().getClassLoader(),
                interfaces.toArray(Class[]::new),
                getInvocationHandler());
    }

    private InvocationHandler getInvocationHandler() {
        return (proxy, method, args) -> {
            for (int i = 0; i < method.getParameterCount(); i++) {
                if (args[i] == null) {
                    throw new NullParameterException(method.getParameters()[i]);
                }
            }
            var res = method.invoke(object, args);
            if (res == null) {
                throw new NullResultException(method);
            }
            return res;
        };
    }

    public static class ProxyBuilder<T> {

        private final T object;

        private ProxyBuilder(T object) {
            Objects.requireNonNull(object);
            this.object = object;
        }

        @SafeVarargs public final NullSafeProxy<T> withInterfaces(Class<? extends T>... interfaces) {
            return new NullSafeProxy<>(object, Arrays.asList(interfaces));
        }
    }

}
