package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/17/2017.
 */
public class MenuController {

    @FXML
    Button serverStartButton;

    @FXML
    Button clientStartButton;

    @FXML
    Button loadFromFileButton;

    @FXML
    TextField ipTextField;

    @FXML
    Label fileNameLabel;

    PaintWithFriends menu;

    boolean loadingFromFile = false;
    String fileName;

    public void initializeBackRef (PaintWithFriends menu) {
        this.menu = menu;
    }

    public void serverStart () {
        System.out.println("Starting as server. Looking for IP: " + ipTextField.getText());
        PaintCanvasNetwork pcn = new PaintCanvasNetwork();
        pcn.initializeFromGui(true, ipTextField.getText(), menu);
        pcn.start(new Stage());
    }

    public void clientStart () {
        System.out.println("Starting as client. Looking for IP: " + ipTextField.getText());
        PaintCanvasNetwork pcn = new PaintCanvasNetwork();
        pcn.initializeFromGui(false, ipTextField.getText(), menu);
        pcn.start(new Stage());

        if (loadingFromFile) {
            System.out.println("Loading from file " + fileName);
            pcn.readRecordFromFile(fileName);
        }

    }

    public void loadFromFile () throws Exception {
        loadingFromFile = true;
        FileChooserDialog fcd = new FileChooserDialog(true, null, menu);
        fcd.start(new Stage());
    }

    public void setFileNameLabel (String text) {
        fileNameLabel.setText(text);
        fileName = text;
    }
}
