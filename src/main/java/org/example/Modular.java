package org.example;

import javafx.scene.layout.Pane;

/**
 * Methods implemented by a {@code Modular} application.
 */
public interface Modular {

    public Pane createApp();

    public String getName();

    public String getShortName();
}
