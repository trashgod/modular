package org.example.gui;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.Modular;

/**
 * A view of the properties in a BlendModel.
 */
public final class BlendView extends Pane {

    private static final int S = 8;
    private final StringProperty argbProperty = new SimpleStringProperty();
    private final BlendModel model;
    private final Canvas canvas = new Canvas();
    private final InvalidationListener listener = (o) -> update();
    private final BlendMode mode = canvas.getGraphicsContext2D().getGlobalBlendMode();
    private final Color darkCol = Color.web("#c0c0c0");
    private final Color lightCol = Color.web("#404040");
    private WritableImage image;

    public BlendView(BlendModel model) {
        this.model = model;
        this.getChildren().add(canvas);
        this.setPrefSize(Modular.WIDTH, Modular.HEIGHT);
        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());
        this.widthProperty().addListener(listener);
        this.heightProperty().addListener(listener);
        this.model.modeProperty().set(mode);
        this.model.modeProperty().addListener(listener);
        this.model.topProperty().addListener(listener);
        this.model.botProperty().addListener(listener);
        this.canvas.setOnMouseMoved(e -> {
            int aRGB = image.getPixelReader().getArgb((int) e.getX(), (int) e.getY());
            argbProperty.set(Integer.toHexString(aRGB).toUpperCase());
        });
        this.canvas.setOnMouseExited(e -> argbProperty.set(""));
        Tooltip tooltip = new Tooltip();
        Tooltip.install(this.canvas, tooltip);
        tooltip.textProperty().bind(argbProperty);
    }

    public StringProperty argbProperty() {
        return argbProperty;
    }

    public void copyImage() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putImage(image);
        clipboard.setContent(content);
    }

    private void update() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = this.getWidth();
        double h = this.getHeight();
        g.setGlobalBlendMode(mode);
        g.setFill(darkCol);
        g.fillRect(0, 0, w, h);
        g.setFill(lightCol);
        double rows = h / S + 1;
        double cols = w / S + 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r + c) % 2 == 0) {
                    g.fillRect(c * S, r * S, S, S);
                }
            }
        }
        g.setGlobalBlendMode(model.modeProperty().get());
        g.setFill(model.botProperty().get());
        g.fillOval(w / 3, h / 6, 2 * w / 3, 2 * h / 3);
        g.setFill(model.topProperty().get());
        g.fillOval(0, h / 6, 2 * w / 3, 2 * h / 3);
        if (w > 0 && h > 0) {
            int wide = (int) w;
            int high = (int) h;
            image = canvas.snapshot(
                new SnapshotParameters(), new WritableImage(wide, high));
        }
    }
}
