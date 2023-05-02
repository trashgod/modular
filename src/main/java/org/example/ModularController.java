package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * {@code Modular} controller factories.
 */
public class ModularController {

    /**
     * A {@code Callback} implementation suitable for a {@code FXMLLoader}
     * controller factory to inject an instance of {@code Application} and
     * {@code Stage} as constructor parameters.
     *
     * @see <a href="https://stackoverflow.com/a/33100968/230513"><i>Solution
     * 2</i>.</a>
     */
    public static class HomeFactory implements Callback<Class<?>, Object> {

        private final Application application;
        private final Stage stage;

        /**
         * Create a {@code Callback} to inject the host {@code Application} and
         * {@code Stage}.
         *
         * @param application the host application.
         * @param stage the host application's stage.
         */
        public HomeFactory(Application application, Stage stage) {
            this.application = application;
            this.stage = stage;
        }

        @Override
        public Object call(Class<?> type) {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 2
                        && c.getParameterTypes()[0] == Application.class
                        && c.getParameterTypes()[1] == Stage.class) {
                        return c.newInstance(application, stage);
                    }
                }
                return type.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException
                | SecurityException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Constructor parameter list.");
            }
        }
    }

    /**
     * A {@code Callback} implementation suitable for a {@code FXMLLoader}
     * controller factory to inject an instance of {@code HostServices} as a
     * constructor parameter.
     *
     * @see <a href="https://stackoverflow.com/a/33100968/230513"><i>Solution
     * 2</i>.</a>
     */
    public static class HostServicesFactory implements Callback<Class<?>, Object> {

        private final HostServices hostServices;

        /**
         * Create a {@code Callback} to inject {@code HostServices}.
         *
         * @param hostServices the application's host services.
         */
        public HostServicesFactory(HostServices hostServices) {
            this.hostServices = hostServices;
        }

        @Override
        public Object call(Class<?> type) {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1
                        && c.getParameterTypes()[0] == HostServices.class) {
                        return c.newInstance(hostServices);
                    }
                }
                return type.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException
                | SecurityException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Constructor parameter list.");
            }
        }
    }
}
