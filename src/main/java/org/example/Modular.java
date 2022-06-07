package org.example;

import javafx.scene.Node;

/**
 * Methods implemented by a {@code Modular} application. A {@code Modular}
 * application may extend {@code Application}, but it is not <i>required</i> to
 * do so.
 */
public interface Modular {

    /**
     * Default preferred width for a node that {@code isResizable()}.
     */
    public static final double WIDTH = 500;
    /**
     * Default preferred height for a node that {@code isResizable()}.
     */
    public static final double HEIGHT = 500;
    /**
     * A {@code Runnable} that does nothing; suitable for default methods.
     */
    public static final Runnable doNothing = new Runnable() {
        @Override
        public void run() {
        }
    };

    /**
     * Return the application's content.
     *
     * @return the {@code Modular} application's content
     */
    public Node createContent();

    /**
     * Return the application's full name.
     *
     * @return the {@code Modular} application's full name
     */
    public String getName();

    /**
     * Return the application's short name.
     *
     * @return the {@code Modular} application's short name, suitable for a
     * title
     */
    public String getShortName();

    /**
     * Return an optional {@code Runnable} to be scheduled when the content is
     * selected.
     *
     * @return an optional {@code Runnable}; the default does nothing.
     */
    default public Runnable whenSelected() {
        return doNothing;
    }

    /**
     * Return an optional {@code Runnable} to be scheduled when the content is
     * not selected.
     *
     * @return an optional {@code Runnable}; the default does nothing.
     */
    default public Runnable whenDeselected() {
        return doNothing;
    }
}
