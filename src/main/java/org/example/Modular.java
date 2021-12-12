package org.example;

import javafx.scene.Node;

/**
 * Methods implemented by a {@code Modular} application. A {@code Modular}
 * application may extend {@code Application}, but it is not <i>required</i> to
 * do so.
 */
public interface Modular {

    public static final double WIDTH = 500;
    public static final double HEIGHT = 500;

    /**
     * @return the {@code Modular} application's content
     */
    public Node createContent();

    /**
     * @return the {@code Modular} application's full name
     */
    public String getName();

    /**
     * @return the {@code Modular} application's short name, suitable for a
     * {@code Tab} title
     */
    public String getShortName();
}
