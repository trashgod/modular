package org.example.math;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A {@code Graph} that models chords connecting points on a circle.
 *
 */
public class Graph {

    /**
     * Default number of points
     */
    public static final int POINTS = 128;

    /**
     * Default multiplier
     */
    public static final double MULTIPLIER = 2.0;

    /** The number of points in this Graph. */
    private final IntegerProperty p = new SimpleIntegerProperty(POINTS);
    /** The multiplier used to connect points on this Graph. */
    private final DoubleProperty m = new SimpleDoubleProperty(MULTIPLIER);

    /**
     * A {@code Graph} that models chords connecting points on a circle.
     */
    public Graph() {
    }

    public IntegerProperty pProperty() {
        return p;
    }

    public DoubleProperty mProperty() {
        return m;
    }
}
