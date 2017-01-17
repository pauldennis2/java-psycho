package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Paul Dennis on 1/17/2017.
 */
public class RunnableGCAlert implements Runnable {

    GraphicsContext gc;

    PaintCanvasNetwork app;

    public RunnableGCAlert (GraphicsContext graphicsContext, PaintCanvasNetwork pcn) {
        gc = graphicsContext;
        app = pcn;
    }

    public void run () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Other user is requesting control.");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            System.out.println("You accepted and passed control");
            app.setInControl(false);
        } else {
            // ... user chose CANCEL or closed the dialog
            System.out.println("You refused the control request");
        }
    }
}
