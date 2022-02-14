package org.example.math;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import org.example.Modular;

/**
 * A {@code Pane} that renders a view of a {@code Graph}.
 *
 * @see <a href="https://stackoverflow.com/a/2510048/230513"><i>Draw a circle
 * with a radius and points around the edge.</i></a>
 */
public class GraphView extends Pane {

    /**
     * Default background color
     */
    public static final Color bgColor = Color.BLACK;
    /**
     * Default foreground color
     */
    public static final Color fgColor = Color.CYAN;
    private static final int INSET = 8;
    /** This graph's background color property. */
    private final ObjectProperty<Color> bg = new SimpleObjectProperty<>(bgColor);
    /** This graph's foreground color property. */
    private final ObjectProperty<Color> fg = new SimpleObjectProperty<>(fgColor);
    /** This graph's rotated property. */
    private final BooleanProperty rot = new SimpleBooleanProperty(true);
    private final Graph graph;
    private final Canvas canvas = new Canvas();
    private final InvalidationListener listener = (o) -> update();

    /**
     * A {@code Pane} that renders a view of a {@code Graph}.
     * 
     * @param graph the model displayed by this view 
     */
    public GraphView(Graph graph) {
        this.graph = graph;
        this.getChildren().add(canvas);
        this.setPrefSize(Modular.WIDTH, Modular.HEIGHT);
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener(listener);
        this.heightProperty().addListener(listener);
        this.rot.addListener(listener);
        this.bg.addListener(listener);
        this.fg.addListener(listener);
        this.graph.pProperty().addListener(listener);
        this.graph.mProperty().addListener(listener);
        update();
    }

    private void update() {
        // clear canvas; connect every m points around a circle with lines
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = this.getWidth();
        double h = this.getHeight();
        double cx = w / 2;
        double cy = h / 2;
        double r = Math.min(w, h) / 2 - INSET;
        g.setFill(bg.get());
        g.fillRect(0, 0, w, h);
        g.setStroke(fg.get());
        g.strokeArc(cx - r, cy - r, 2 * r, 2 * r, 0, 359, ArcType.CHORD);
        double offset = rot.get() ? Math.PI : 0;
        for (int i = 0; i < graph.pProperty().get(); i++) {
            double t1 = 2 * Math.PI * i / graph.pProperty().get() + offset;
            double x1 = Math.cos(t1) * r + cx;
            double y1 = Math.sin(t1) * r + cy;
            double j = i * graph.mProperty().get() % graph.pProperty().get();
            double t2 = 2 * Math.PI * j / graph.pProperty().get() + offset;
            double x2 = Math.cos(t2) * r + cx;
            double y2 = Math.sin(t2) * r + cy;
            g.strokeLine(x1, y1, x2, y2);
        }
    }

    public ObjectProperty<Color> bgProperty() {
        return bg;
    }

    public ObjectProperty<Color> fgProperty() {
        return fg;
    }

    public BooleanProperty rotProperty() {
        return rot;
    }

    /**
     * @return this graph's display {@code Canvas}
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
