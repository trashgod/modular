package org.example.status;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.util.Duration;
import org.example.Modular;

/**
 * Status application controller
 */
public class StatusController implements Modular {

    private static final int MEBIBYTE = 1024 * 1024;
    private static final double SECONDS = 60 * 2;
    @FXML
    private Parent root;
    @FXML
    private String name;
    @FXML
    private String shortName;
    @FXML
    private String tmName;
    @FXML
    private String umName;
    @FXML
    private LineChart chart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button gc;

    private final XYChart.Series<Number, Number> total = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> used = new XYChart.Series<>();

    @FXML
    public void initialize() {
        chart.getData().addAll(total, used);
        total.setName(tmName);
        used.setName(umName);
        xAxis.setUpperBound(SECONDS);
        var start = LocalTime.now();
        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            private DateTimeFormatter f = DateTimeFormatter.ofPattern("mm:ss");

            @Override
            public String toString(Number value) {
                return f.format(start.plusSeconds(value.longValue()));
            }
        });
        var timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
            Duration.seconds(1), new EventHandler<ActionEvent>() {
            private long count;

            @Override
            public void handle(ActionEvent t) {
                long tm = Runtime.getRuntime().totalMemory();
                long fm = Runtime.getRuntime().freeMemory();
                total.getData().add(new XYChart.Data<>(count, tm / MEBIBYTE));
                used.getData().add(new XYChart.Data<>(count, (tm - fm) / MEBIBYTE));
                count++;
                if (total.getData().size() > SECONDS) {
                    total.getData().remove(0);
                    used.getData().remove(0);
                    xAxis.setLowerBound(xAxis.getLowerBound() + 1);
                    xAxis.setUpperBound(xAxis.getUpperBound() + 1);
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void handleGC(ActionEvent event) {
        Runtime.getRuntime().gc();
    }

    @Override
    public Parent createContent() {
        return root;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getShortName() {
        return shortName;
    }
}
