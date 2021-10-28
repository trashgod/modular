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
    private final IntegerProperty p = new SimpleIntegerProperty(POINTS);
    private final DoubleProperty m = new SimpleDoubleProperty(MULTIPLIER);

    public Graph() {
    }

    /**
     * @return this graph's number of points property
     */
    public IntegerProperty pProperty() {
        return p;
    }

    /**
     * @return this graph's multiplier property
     */
    public DoubleProperty mProperty() {
        return m;
    }
}
