package org.example.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

/**
 * A model holding HSB color properties, hue, saturation, brightness, opacity and color.
 */
public final class HSBModel {

    private final Color initialColor;
    /** The current color. */
    private final ObjectProperty<Color> c = new SimpleObjectProperty<>();
    /** Hue of the current color. */
    private final DoubleProperty h = new SimpleDoubleProperty();
    /** Saturation of the current color. */
    private final DoubleProperty s = new SimpleDoubleProperty();
    /** Brightness of the current color. */
    private final DoubleProperty b = new SimpleDoubleProperty();
    /** Alpha (opacity) of the current color. */
    private final DoubleProperty a = new SimpleDoubleProperty();
    private final InvalidationListener listener = (o) -> {
        c.set(Color.hsb(h.get(), s.get(), b.get(), a.get()));
    };

    public HSBModel(Color color) {
        this.initialColor = color;
        c.set(color);
        h.addListener(listener);
        s.addListener(listener);
        b.addListener(listener);
        a.addListener(listener);
    }

    public void setColor(Color c) {
        h.set(c.getHue());
        s.set(c.getSaturation());
        b.set(c.getBrightness());
        a.set(c.getOpacity());
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

    public DoubleProperty aProperty() {
        return a;
    }
}