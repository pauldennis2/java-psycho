package sample;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//import javafx.scene.control.Alert;

/**
 * Created by dbashizi on 12/15/16.
 */
public class ConnectionHandler implements Runnable {
    private Socket clientSocket;
    // this is how we read from the client
    private BufferedReader inputFromClient;
    private PrintWriter outputToClient;

    private PaintCanvasNetwork app;

    GraphicsContext gc;
    double strokeSize = 2.0;

    public ConnectionHandler(Socket clientSocket,
                             BufferedReader inputFromClient,
                             PrintWriter outputToClient,
                             GraphicsContext gc,
                             PaintCanvasNetwork app) {
        this.clientSocket = clientSocket;
        this.inputFromClient = inputFromClient;
        this.outputToClient = outputToClient;
        this.gc = gc;
        this.app = app;
    }

    public void run() {
        try {
            // display information about who just connected to our server
            System.out.println("Ready for messages from " + clientSocket.getInetAddress().getHostAddress());

            // read from the input until the client disconnects
            String inputLine;
            while ((inputLine = inputFromClient.readLine()) != null) {
                System.out.println("Received message: " + inputLine + " from " + clientSocket.toString());
                if (inputLine.equals(SimpleServer.CONNECTION_END)) {
                    System.out.println("**** Closing this connection");
                    outputToClient.println("!!! Closing this connection !!!");
                    break;
                } else {
                    // this is where I do actual work with the message from the client
                    outputToClient.println("ECHO::" + inputLine);
                    if (inputLine.startsWith("Stroke=")) {
                        app.getRecord().add(inputLine);
                        String[] splitResponse = inputLine.split("=");
                        Stroke stroke = jsonStrokeRestore(splitResponse[1]);
                        Platform.runLater(new RunnableGCStroke(gc, stroke, strokeSize));
                    }
                    if (inputLine.startsWith("Change=")) {
                        app.getRecord().add(inputLine);
                        String[] splitResponse = inputLine.split("=");
                        StrokeChange strokeChange = jsonStrokeChangeRestore(splitResponse[1]);
                        if (strokeSize != strokeChange.getStrokeSize()) {
                            strokeSize = strokeChange.getStrokeSize();
                        } else {
                            Platform.runLater(new RunnableGCStrokeChange(gc, strokeChange));
                        }
                        //RunnableGCStrokeChange responsible for clearing screen if needed
                    }
                    if (inputLine.startsWith("Control")) {
                        //Control and accept don't need to be recorded because we don't care who painted something
                        System.out.println("We are in control? Control = " + app.getInControl());
                        System.out.println("Our app is " + app);
                        System.out.println("Our client is: " + app.getClient());
                        System.out.println("Other use wants control of the drawing. Allow? (Y/N)");
                        Platform.runLater(new RunnableGCAlert(gc, app));

                        /*Scanner inputScanner = new Scanner(System.in);
                        String response = inputScanner.nextLine().toLowerCase();
                        if (response.contains("y")) {
                            System.out.println("Giving control");
                            app.setInControl(false);
                        } else if (response.contains("n")) {
                            System.out.println("No control");
                        } else {
                            System.out.println("You're confused (defaulted to no)");
                        }*/
                    }
                    if (inputLine.startsWith("Accept")) {
                        System.out.println("Other user has given control. Hooray!");
                        app.setInControl(true);
                    }
                }
            }
            System.out.println("**** Connection Handler closing!");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String jsonSave (StrokeChange strokeChange) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(strokeChange);
        return jsonString;
    }

    public static String jsonSave (Stroke stroke) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(stroke);
        return jsonString;
    }

    public static Stroke jsonStrokeRestore (String jsonString) {
        JsonParser listParser = new JsonParser();
        Stroke stroke = listParser.parse(jsonString, Stroke.class);
        return stroke;
    }

    public static StrokeChange jsonStrokeChangeRestore (String jsonString) {
        JsonParser listParser = new JsonParser();
        System.out.println("Received string: " + jsonString);
        StrokeChange strokeChange = listParser.parse(jsonString, StrokeChange.class);
        return strokeChange;
    }
}
