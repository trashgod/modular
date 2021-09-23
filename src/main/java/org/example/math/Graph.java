package org.example.math;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * A {@code Graph} that renders chords connecting points on a circle.
 *
 * @see <a href="https://stackoverflow.com/a/2510048/230513"><i>Draw a circle with
 * a radius and points around the edge.</i></a>
 */
public class Graph {

    public static final int POINTS = 128;
    public static final double MULTIPLIER = 2.0;
    private static final double WIDTH = 500;
    private static final double HEIGHT = 500;
    private static final int INSET = 8;
    private final Pane pane = new Pane();
    private final Canvas canvas = new Canvas();
    private final IntegerProperty p = new SimpleIntegerProperty(POINTS);
    private final DoubleProperty m = new SimpleDoubleProperty(MULTIPLIER);
    private final ObjectProperty<Color> bg = new SimpleObjectProperty<>(Color.BLACK);
    private final ObjectProperty<Color> fg = new SimpleObjectProperty<>(Color.CYAN);
    private final BooleanProperty flip = new SimpleBooleanProperty(true);
    private final InvalidationListener listener = ((o) -> update());

    public Graph() {
        pane.getChildren().add(canvas);
        pane.setPrefSize(WIDTH, HEIGHT);
        canvas.widthProperty().bind(pane.widthProperty());
        canvas.heightProperty().bind(pane.heightProperty());
        pane.widthProperty().addListener(listener);
        pane.heightProperty().addListener(listener);
        p.addListener(listener);
        m.addListener(listener);
        flip.addListener(listener);
        bg.addListener(listener);
        fg.addListener(listener);
        update();
    }

    private void update() {
        // clear canvas; connect every m points around a circle with lines
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = pane.getWidth();
        double h = pane.getHeight();
        double cx = w / 2;
        double cy = h / 2;
        double r = Math.min(w, h) / 2 - INSET;
        g.setFill(bg.get());
        g.fillRect(0, 0, w, h);
        g.setStroke(fg.get());
        g.strokeArc(cx - r, cy - r, 2 * r, 2 * r, 0, 359, ArcType.CHORD);
        double offset = flip.get() ? Math.PI : 0;
        for (int i = 0; i < p.get(); i++) {
            double t1 = 2 * Math.PI * i / p.get() + offset;
            double x1 = Math.cos(t1) * r + cx;
            double y1 = Math.sin(t1) * r + cy;
            double j = i * m.get() % p.get();
            double t2 = 2 * Math.PI * j / p.get() + offset;
            double x2 = Math.cos(t2) * r + cx;
            double y2 = Math.sin(t2) * r + cy;
            g.strokeLine(x1, y1, x2, y2);
        }
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

    /**
     * @return this graph's background color property
     */
    public ObjectProperty<Color> bgProperty() {
        return bg;
    }

    /**
     * @return this graph's foreground color property
     */
    public ObjectProperty<Color> fgProperty() {
        return fg;
    }

    /**
     * @return this graph's flipped property
     */
    public BooleanProperty flipProperty() {
        return flip;
    }

    /**
     * @return this graph's parent node
     */
    public Pane getPane() {
        return pane;
    }
}
