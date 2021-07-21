package com.nutrymaco.proxy;

import com.nutrymaco.tester.annotations.Test;
import com.nutrymaco.tester.asserting.AssertEquals;
import com.nutrymaco.tester.executing.TestExecutor;

public class SimpleTest {

    public static void main(String[] args) {
        TestExecutor.of().execute(new SimpleTest());
    }

    @Test
    public void testProxyCreated() {
        interface Service {

            String function1(String arg1, int arg2);

        }

        Service service = new Service() {
            @Override public String function1(String arg1, int arg2) {
                return "result";
            }
        };

        Service nullSafeService = NullSafeProxy.of(service).withInterfaces(Service.class).create();
    }

    @Test
    public void testExceptionWhenNullPass() throws NoSuchMethodException {
        interface Service {

            String function1(String arg1, int arg2);

        }

        Service service = new Service() {
            @Override public String function1(String arg1, int arg2) {
                return "result";
            }
        };

        Service nullSafeService = NullSafeProxy.of(service).withInterfaces(Service.class).create();

        try {
            nullSafeService.function1(null, 12);
            throw new RuntimeException("wrong exception");
        } catch (Exception exception) {
            var function1 = Service.class.getMethod("function1", String.class, int.class);
            var expectedException = new NullParameterException(function1.getParameters()[0]);
            AssertEquals
                    .actual(exception)
                    .expect(expectedException);
        }
    }

    @Test
    public void testExceptionWhenNullResult() throws NoSuchMethodException {
        interface Service {

            String function1(String arg1, int arg2);

        }

        Service service = new Service() {
            @Override public String function1(String arg1, int arg2) {
                return null;
            }
        };

        Service nullSafeService = NullSafeProxy.of(service).withInterfaces(Service.class).create();

        try {
            nullSafeService.function1("1", 2);
        } catch (Exception exception) {
            var function1= Service.class.getMethod("function1", String.class, int.class);
            AssertEquals
                    .actual(exception)
                    .expect(new NullResultException(function1));
        }
    }


}
