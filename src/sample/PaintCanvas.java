package sample;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Paul Dennis on 1/11/2017.
 */
public class PaintCanvas extends Application {
    //r, g, b

    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    double strokeSize = 2.0;
    boolean drawing = true;

    @Override
    public void start (Stage primaryStage) {
        Group rootGroup = new Group();

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setFocusTraversable(true);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();



        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (drawing) {
                    graphicsContext.strokeOval(event.getX(), event.getY(), strokeSize, strokeSize);
                }
            }
        });

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                switch (event.getCode()) {
                    case D:
                        if (drawing) {
                            System.out.println("Disabling drawing");
                            drawing = false;
                        } else {
                            System.out.println("Enabling drawing");
                            drawing = true;
                        }
                        break;
                    case R:
                        System.out.println("Setting color to red");
                        graphicsContext.setStroke(Color.color(1, 0, 0));
                        break;
                    case G:
                        System.out.println("Setting color to green");
                        graphicsContext.setStroke(Color.color(0, 1, 0));
                        break;
                    case B:
                        System.out.println("Setting color to blue");
                        graphicsContext.setStroke(Color.color(0, 0, 1));
                        break;
                    case C:
                        System.out.println("Clearing screen.");
                        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        break;
                    case SPACE:
                        System.out.println("Setting color to random");
                        graphicsContext.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                        break;
                    case UP:
                        System.out.println("Increasing stroke size");
                        if (strokeSize < 20) {
                            strokeSize++;
                        }
                        break;
                    case DOWN:
                        System.out.println("Decreasing stroke size");
                        if (strokeSize > 1) {
                            strokeSize--;
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        rootGroup.getChildren().add(canvas);

        Scene scene = new Scene(rootGroup, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
