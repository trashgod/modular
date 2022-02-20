package org.example.gui;

import javafx.beans.InvalidationListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.Modular;

/**
 * A view of the properties in a BlendModel.
 */
public class BlendView extends Pane {

    private final BlendModel model;
    private final Canvas canvas = new Canvas();
    private final InvalidationListener listener = (o) -> update();

    public BlendView(BlendModel model) {
        this.model = model;
        this.getChildren().add(canvas);
        this.setPrefSize(Modular.WIDTH, Modular.HEIGHT);
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener(listener);
        this.heightProperty().addListener(listener);
        BlendMode mode = canvas.getGraphicsContext2D().getGlobalBlendMode();
        this.model.modeProperty().set(mode);
        this.model.modeProperty().addListener(listener);
        this.model.topProperty().addListener(listener);
        this.model.botProperty().addListener(listener);
    }

    private void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = this.getWidth();
        double h = this.getHeight();
        g.setFill(Color.BLACK);
        g.fillRect(0, 0, w, h);
        g.setFill(model.topProperty().get());
        g.fillOval(0, h / 4, 2 * w / 3, h / 2);
    }
}