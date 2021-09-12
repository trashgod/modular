package org.example.math;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

/**
 * @see https://www.youtube.com/watch?v=qhbuKbxJsk8
 */
public class ModularApp extends Application {

    private static class MAGraph {

        private static final double WIDTH = 500;
        private static final double HEIGHT = 500;
        private static final int INSET = 8;
        private static final int POINTS = 128;
        private static final double MULTIPLIER = 2.0;
        private final Pane pane = new Pane();
        private final Canvas canvas = new Canvas();
        private final IntegerProperty p = new SimpleIntegerProperty(POINTS);
        private final DoubleProperty m = new SimpleDoubleProperty(MULTIPLIER);
        private final ObjectProperty<Color> c = new SimpleObjectProperty<>(Color.BLUE);
        private final InvalidationListener listener = ((o) -> update());

        public MAGraph() {
            pane.getChildren().add(canvas);
            pane.setPrefSize(WIDTH, HEIGHT);
            canvas.widthProperty().bind(pane.widthProperty());
            canvas.heightProperty().bind(pane.heightProperty());
            pane.widthProperty().addListener(listener);
            pane.heightProperty().addListener(listener);
            p.addListener(listener);
            m.addListener(listener);
            c.addListener(listener);
            update();
        }   

        private void update() {
            //clear canvas; connect every m points around a circle with lines
            //https://stackoverflow.com/a/2510048/230513
            GraphicsContext g = canvas.getGraphicsContext2D();
            double w = pane.getWidth();
            double h = pane.getHeight();
            double cx = w / 2;
            double cy = h / 2;
            double r = Math.min(w, h) / 2 - INSET;
            g.setFill(Color.WHITE);
            g.fillRect(0, 0, w, h);
            g.setStroke(c.get());
            g.strokeArc(cx - r, cy - r, 2 * r, 2 * r, 0, 359, ArcType.CHORD);
            for (int i = 0; i < p.get(); i++) {
                double t1 = 2 * Math.PI * i / p.get() + Math.PI;
                double x1 = Math.cos(t1) * r + cx;
                double y1 = Math.sin(t1) * r + cy;
                double j = (i * m.get()) % ((double) p.get());
                double t2 = 2 * Math.PI * j / p.get() + Math.PI;
                double x2 = Math.cos(t2) * r + cx;
                double y2 = Math.sin(t2) * r + cy;
                g.strokeLine(x1, y1, x2, y2);
            }
        }

        public Pane getPane() {
            return pane;
        }

        public ToolBar createToolBar() {
            Spinner<Number> mSpinner = new Spinner<>(2, 256, MULTIPLIER, 0.1);
            mSpinner.setEditable(true);
            m.bindBidirectional(mSpinner.getValueFactory().valueProperty());
            Spinner<Number> pSpinner = new Spinner<>(2, 256, POINTS);
            pSpinner.setEditable(true);
            p.bindBidirectional(pSpinner.getValueFactory().valueProperty());
            ColorPicker colorPicker = new ColorPicker(c.get());
            colorPicker.setOnAction((ActionEvent e) -> {
                c.set(colorPicker.getValue());
            });
            return new ToolBar(new Label("Multiplier:"), mSpinner,
                new Label("Points:"), pSpinner, colorPicker);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modular Arithmetic Graph");
        MAGraph graph = new MAGraph();
        BorderPane root = new BorderPane();
        Label t = new Label("Adjust the controls below to change the graph.");
        BorderPane.setAlignment(t, Pos.CENTER);
        root.setTop(t);
        root.setCenter(graph.getPane());
        root.setBottom(graph.createToolBar());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
