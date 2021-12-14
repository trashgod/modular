package org.example.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 * A model holding HSB color properties, hue, saturation, brightness and color. 
 */
public final class HSBModel {

    private final Color initialColor;
    private final ObjectProperty<Color> c = new SimpleObjectProperty<>();
    private final DoubleProperty h = new SimpleDoubleProperty();
    private final DoubleProperty s = new SimpleDoubleProperty();
    private final DoubleProperty b = new SimpleDoubleProperty();
    private final InvalidationListener listener = (o) -> {
        c.set(Color.hsb(h.get(), s.get(), b.get()));
    };

    public HSBModel(Color color) {
        this.initialColor = color;
        c.set(color);
        h.addListener(listener);
        s.addListener(listener);
        b.addListener(listener);
    }

    public void setColor(Color c) {
        h.set(c.getHue());
        s.set(c.getSaturation());
        b.set(c.getBrightness());
    }

    public Color getInitialColor() {
        return initialColor;
    }

    public ObjectProperty<Color> cProperty() {
        return c;
    }

    public DoubleProperty hProperty() {
        return h;
    }

    public DoubleProperty sProperty() {
        return s;
    }

    public DoubleProperty bProperty() {
        return b;
    }
}
