package org.example.math;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An {@code HTree} model of order <i>n</i>.
 */
public class HTreeModel {

    /**
     * Minimum order.
     */
    public static final int MIN = 0;

    /**
     * Maximum order.
     */
    public static final int MAX = 9;

    /**
     * Default order.
     */
    public static final int ORDER = 4;
    /**
     * Order of this HTree.
     */
    private final IntegerProperty n = new SimpleIntegerProperty(ORDER);

    /**
     * An {@code HTree} model of order <i>n</i>.
     */
    public HTreeModel() {
    }

    public IntegerProperty nProperty() {
        return n;
    }

}
