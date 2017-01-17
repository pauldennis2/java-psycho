package sample;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Paul Dennis on 1/11/2017.
 */
public class PaintCanvasNetwork extends Application {

    public static final int CANVAS_WIDTH = 300;
    public static final int CANVAS_HEIGHT = 200;

    double strokeSize = 2.0;
    boolean drawing = false;

    boolean control;

    SimpleClient client;
    SimpleServer server;

    SimpleServer serverListener;
    SimpleClient serverTalker;

    PaintRecord record;

    boolean isServer;
    String ip;

    GraphicsContext graphicsContext;

    PaintCanvasNetwork app;

    PaintWithFriends menu;


    public void initializeFromGui (boolean isServer, String ip, PaintWithFriends menu) {
        System.out.println("isServer: " + isServer);
        System.out.println("ip: " + ip);
        this.ip = ip;
        this.isServer = isServer;
        this.menu = menu;
    }

    public void startWithoutCli (GraphicsContext gc) {
        if (isServer) {
            server = new SimpleServer(gc, SimpleServer.FIRST_PORT_NUM, this);
            Thread serverThread = new Thread(server);
            serverThread.start();
            control = false;
        } else {
            client = new SimpleClient(ip, SimpleServer.FIRST_PORT_NUM);
            serverListener = new SimpleServer(gc, SimpleServer.SECOND_PORT_NUM, this);
            Thread serverListenerThread = new Thread(serverListener);
            serverListenerThread.start();
            control = true;
        }
        record = new PaintRecord();
    }

    @Override
    public void start (Stage primaryStage) {

        app = this;
        Group rootGroup = new Group();

        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setFocusTraversable(true);

        graphicsContext = canvas.getGraphicsContext2D();

        startWithoutCli(graphicsContext);

        rootGroup.getChildren().add(canvas);

        Scene scene = new Scene(rootGroup, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene(scene);
        if (client == null) {
            primaryStage.setTitle("Paint With Friends Server");
        } else {
            primaryStage.setTitle("Paint With Friends Client");
        }
        primaryStage.show();


        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (drawing) {
                    graphicsContext.strokeOval(event.getX(), event.getY(), strokeSize, strokeSize);
                    if (client != null) {
                        try {
                            Stroke stroke = new Stroke(event.getX(), event.getY());
                            String message = "Stroke=" + ConnectionHandler.jsonSave(stroke);
                            client.sendMessage(message);
                            record.add(message);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.V) {
                    //save
                    //System.out.println("Saving drawing to file");
                    //record.writeToFile();
                    FileChooserDialog fcd = new FileChooserDialog(false, app, menu);
                    try {
                        fcd.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                if (control) {
                    System.out.println(event.getCode());
                    String message = null;
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
                            StrokeChange redChange = new StrokeChange(1.0, 0.0, 0.0, (int)strokeSize, false);
                            message = "Change=" + ConnectionHandler.jsonSave(redChange);
                            break;
                        case G:
                            System.out.println("Setting color to green");
                            graphicsContext.setStroke(Color.color(0, 1, 0));
                            StrokeChange greenChange = new StrokeChange(0.0, 1.0, 0.0, (int)strokeSize, false);
                            message = "Change=" + ConnectionHandler.jsonSave(greenChange);
                            break;
                        case B:
                            System.out.println("Setting color to blue");
                            graphicsContext.setStroke(Color.color(0, 0, 1));
                            StrokeChange blueChange = new StrokeChange(0.0, 0.0, 1.0, (int)strokeSize, false);
                            message = "Change=" + ConnectionHandler.jsonSave(blueChange);
                            break;
                        case C:
                            System.out.println("Clearing screen.");
                            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            StrokeChange clearChange = new StrokeChange(0.0, 0.0, 0.0, (int)strokeSize, true);
                            message = "Change=" + ConnectionHandler.jsonSave(clearChange);
                            break;
                        case SPACE:
                            System.out.println("Setting color to random");
                            double red = Math.random();
                            double green = Math.random();
                            double blue = Math.random();
                            graphicsContext.setStroke(Color.color(red, green, blue));
                            StrokeChange randomColorChange = new StrokeChange(red, green, blue, (int)strokeSize, false);
                            message = "Change=" + ConnectionHandler.jsonSave(randomColorChange);
                            break;
                        case UP:
                            if (strokeSize < 20) {
                                System.out.println("Increasing stroke size");
                                strokeSize++;
                                StrokeChange sizeIncrease = new StrokeChange(0, 0, 0, (int)strokeSize, false);
                                message = "Change=" + ConnectionHandler.jsonSave(sizeIncrease);
                            }
                            break;
                        case DOWN:
                            if (strokeSize > 1) {
                                System.out.println("Decreasing stroke size");
                                strokeSize--;
                                StrokeChange sizeDecrease = new StrokeChange(0, 0, 0, (int)strokeSize, false);
                                message = "Change=" + ConnectionHandler.jsonSave(sizeDecrease);
                            }
                            break;
                        default:
                            break;
                    }
                    if (message != null) {
                        record.add(message);
                        try {
                            client.sendMessage(message);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {//End if control
                    if (event.getCode() == KeyCode.Q) {
                        if (client == null) {
                            System.out.println("Requesting control");
                            /*if (serverTalker == null) {
                                serverTalker = new SimpleClient(ip, SimpleServer.SECOND_PORT_NUM);
                            }*/
                            client = new SimpleClient(ip, SimpleServer.SECOND_PORT_NUM);
                            try {
                                client.sendMessage("Control");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            System.out.println("Requesting control back");
                            try {
                                client.sendMessage("Control");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        System.out.println("You're not in control. You're dangerously out of control!");
                        System.out.println("Press Q to reQuest control");
                    }
                }
            }
        });
    }

    public void setInControl (boolean control) {
        System.out.println("In app.setInControl()");
        System.out.println("Our app is " + this);
        System.out.println("Before: control = " + this.control);
        this.control = control;
        System.out.println("After: control = " + control);
        if (client != null) {
            if (!control) {
                try {
                    System.out.println("Sending accept message");
                    client.sendMessage("Accept");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void writeRecordToFile (String fileName) {
        record.writeToFile(fileName);
    }

    public void readRecordFromFile (String fileName) {
        record = PaintRecord.readFromFile(fileName);
        for (String string : record.getRecord()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(string);
            String splitString[] = string.split("=");
            if (string.startsWith("Stroke=")) {
                Stroke stroke = ConnectionHandler.jsonStrokeRestore(splitString[1]);
                graphicsContext.strokeOval(stroke.getX(), stroke.getY(), strokeSize, strokeSize);
            }
            if (string.startsWith("Change=")) {
                StrokeChange strokeChange = ConnectionHandler.jsonStrokeChangeRestore(splitString[1]);
                if (strokeChange.getStrokeSize() != strokeSize) {
                    strokeSize = strokeChange.getStrokeSize();
                } else {
                    graphicsContext.setStroke(Color.color(strokeChange.getRed(), strokeChange.getGreen(), strokeChange.getBlue()));
                }
            }
            try {
                client.sendMessage(string);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean getInControl () {
        return control;
    }

    public SimpleClient getClient () {
        return client;
    }

    public PaintRecord getRecord () {
        return record;
    }
}
