package org.example.math;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An {@code HTree} model of order <i>n</i>.
 */
public class HTreeModel {

    /**
     * Default order.
     */
    public static final int ORDER = 10;
    private final IntegerProperty n = new SimpleIntegerProperty(ORDER);

    /**
     * @return this graph's number of levels property
     */
    public IntegerProperty nProperty() {
        return n;
    }

}
