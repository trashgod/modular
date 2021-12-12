package org.example.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.example.Modular;

/**
 *
 */
final class HSBPane extends Pane {

        private static final int CROSS_SIZE = 8;
        private static final Color CROSS_COLOR = Color.WHITE;
        private final Rectangle r = new Rectangle();
        private final Line h = new Line();
        private final Line v = new Line();

        public HSBPane(HSBModel model) {
            this.setPrefSize(Modular.WIDTH, Modular.HEIGHT);
            this.getChildren().addAll(r, h, v);
            r.widthProperty().bind(this.widthProperty());
            r.heightProperty().bind(this.heightProperty());
            r.setStroke(model.getInitialColor());
            r.strokeProperty().bind(model.cProperty());
            r.setFill(model.getInitialColor());
            r.fillProperty().bind(model.cProperty());
            h.setStroke(CROSS_COLOR);
            h.startXProperty().bind(this.widthProperty().divide(2).subtract(CROSS_SIZE));
            h.endXProperty().bind(this.widthProperty().divide(2).add(CROSS_SIZE));
            h.startYProperty().bind(this.heightProperty().divide(2));
            h.endYProperty().bind(this.heightProperty().divide(2));
            v.setStroke(CROSS_COLOR);
            v.startXProperty().bind(this.widthProperty().divide(2));
            v.endXProperty().bind(this.widthProperty().divide(2));
            v.startYProperty().bind(this.heightProperty().divide(2).subtract(CROSS_SIZE));
            v.endYProperty().bind(this.heightProperty().divide(2).add(CROSS_SIZE));
        }
    }
