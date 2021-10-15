package org.example;

import javafx.scene.Node;

/**
 * Methods implemented by a {@code Modular} application. A {@code Modular}
 * application may extend {@code Application}, but it is not <i>required</i> to
 * do so.
 */
public interface Modular {

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
