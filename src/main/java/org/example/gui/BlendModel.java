package org.example.gui;

import java.io.IOException;
import java.util.Properties;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

/**
 * A model holding properties related to a BlendMode.
 */
public class BlendModel {

    /**
     * The current BlendMode.
     */
    private final ObjectProperty<BlendMode> mode = new SimpleObjectProperty<>();
    /**
     * The top color.
     */
    private final ObjectProperty<Color> top = new SimpleObjectProperty<>(Color.BLUE);
    /**
     * The bottom color.
     */
    private final ObjectProperty<Color> bot = new SimpleObjectProperty<>(Color.RED);
    /**
     * The current BlendMode description.
     */
    private final StringProperty text = new SimpleStringProperty("Description.");
    private static final Properties glossary = new Properties();

    public BlendModel() {
        try (var in = getClass().getResourceAsStream("mode.properties")) {
            glossary.load(in);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        mode.addListener((o) -> {
            BlendMode m = mode.get();
            String s = glossary.getProperty(m.name(), m.name());
            text.set(s);
        });
    }

    public ObjectProperty<BlendMode> modeProperty() {
        return mode;
    }

    public ObjectProperty<Color> topProperty() {
        return top;
    }

    public ObjectProperty<Color> botProperty() {
        return bot;
    }

    public StringProperty textProperty() {
        return text;
    }
}
