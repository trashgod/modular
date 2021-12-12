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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import org.example.Modular;

/**
 * A {@code Pane} that renders a view of an {@code HTree}.
 *
 * @see <a href="https://en.wikipedia.org/wiki/H_tree"><i>H-tree</i>.</a>
 */
public class HTreeView extends Pane {

    /**
     * Default first color
     */
    public static final Color c1Color = Color.hsb(60, 1, 1);
    /**
     * Default second color
     */
    public static final Color c2Color = Color.hsb(300, 1, 1);
    private final ObjectProperty<Color> c1 = new SimpleObjectProperty<>(c1Color);
    private final ObjectProperty<Color> c2 = new SimpleObjectProperty<>(c2Color);
    private final BooleanProperty stroke = new SimpleBooleanProperty(true);
    private final HTreeModel model;
    private final Canvas canvas = new Canvas();
    private final InvalidationListener listener = (o) -> update();

    /**
     * A {@code Pane} that renders a view of an {@code HTree}.
     * 
     * @param model the model rendered by this view
     */
    public HTreeView(HTreeModel model) {
        this.model = model;
        this.getChildren().add(canvas);
        this.setPrefSize(Modular.WIDTH, Modular.HEIGHT);
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener(listener);
        this.heightProperty().addListener(listener);
        this.stroke.addListener(listener);
        this.c1.addListener(listener);
        this.c2.addListener(listener);
        this.model.nProperty().addListener(listener);
        update();
    }

    private void draw(GraphicsContext g, int n, double sz, double x, double y) {
        if (n == 0) {
            return;
        }
        double fraction = (double) n / (HTreeModel.MAX - HTreeModel.MIN);
        Color color = c1.get().interpolate(c2.get(), fraction);
        g.setStroke(color);
        if (stroke.get()) {
            g.setLineWidth(n);
            g.setLineCap(StrokeLineCap.BUTT);
            g.setLineJoin(StrokeLineJoin.MITER);
        }
        double x0 = x - sz / 2, x1 = x + sz / 2;
        double y0 = y - sz / 2, y1 = y + sz / 2;
        // draw the 3 line segments of the H 
        g.strokeLine(x0, y, x1, y);
        g.strokeLine(x0, y0, x0, y1);
        g.strokeLine(x1, y0, x1, y1);
        // recursively draw 4 half-size H-trees of order n - 1
        draw(g, n - 1, sz / 2, x0, y0);
        draw(g, n - 1, sz / 2, x0, y1);
        draw(g, n - 1, sz / 2, x1, y0);
        draw(g, n - 1, sz / 2, x1, y1);
    }

    private void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = this.getWidth();
        double h = this.getHeight();
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, w, h);
        draw(g, model.nProperty().get(), getWidth() / 2, getWidth() / 2, getHeight() / 2);
    }

    /**
     * @return this tree's first color property
     */
    public ObjectProperty<Color> c1Property() {
        return c1;
    }

    /**
     * @return this tree's second color property
     */
    public ObjectProperty<Color> c2Property() {
        return c2;
    }

    /**
     * @return this tree's stroke property
     */
    public BooleanProperty strokeProperty() {
        return stroke;
    }

    /**
     * @return this tree's display {@code Canvas}
     */
    public Canvas getCanvas() {
        return canvas;
    }
}
