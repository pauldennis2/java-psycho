package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Created by Paul Dennis on 1/11/2017.
 */
public class PsychoCanvas extends Application {

    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    public static final int NUM_CIRCLES = 50;
    public static final int CIRCLE_SIZE = 15;


    @Override
    public void start (Stage primaryStage) {
        Group rootGroup = new Group();

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setFocusTraversable(true);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(15);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                double cursor_x = event.getX();
                double cursor_y = event.getY();

                Random random = new Random();

                double random_x;
                double random_y;

                for (int index = 0; index < NUM_CIRCLES; index++) {
                    graphicsContext.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                    random_x = random.nextDouble();
                    random_y = random.nextDouble();

                    graphicsContext.strokeOval(cursor_x * random_x, cursor_y * random_y, CIRCLE_SIZE, CIRCLE_SIZE);
                }
            }
        });

        rootGroup.getChildren().add(canvas);

        Scene scene = new Scene(rootGroup, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
