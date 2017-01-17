package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Paul Dennis on 1/17/2017.
 */
public class FileChooserController implements Initializable {

    boolean loading;
    PaintCanvasNetwork app;
    FileChooserDialog fileChooserDialog;
    PaintWithFriends menu;

    @FXML
    Button loadButton;

    @FXML
    Button saveButton;

    @FXML
    ListView jsonFileListView;

    @FXML
    TextField fileNameTextField;

    @FXML
    Label dotJsonLabel;

    public FileChooserController() {
        //JavaFX requires a default constructor
        super();
    }

    public void setContextInfo (boolean loading, PaintCanvasNetwork pcn, FileChooserDialog fcd, PaintWithFriends pwf) {
        this.loading = loading;
        app = pcn;
        fileChooserDialog = fcd;
        menu = pwf;
        if (loading) {
            saveButton.setDisable(true);
            loadButton.setDisable(false);
            fileNameTextField.setVisible(false);
            dotJsonLabel.setVisible(false);
        } else {
            saveButton.setDisable(false);
            loadButton.setDisable(true);
            fileNameTextField.setVisible(true);
            dotJsonLabel.setVisible(true);
        }
    }

    @Override
    public void initialize (URL url, ResourceBundle resources) {


        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();

        ObservableList<String> jsonFileNames = FXCollections.observableArrayList();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String nameOfFile = listOfFiles[i].getName();
                if (nameOfFile.endsWith(".json")) {
                    jsonFileNames.add(nameOfFile);
                }
            }
        }

        jsonFileListView.setItems(jsonFileNames);
    }

    public void saveButtonPressed () {
        app.writeRecordToFile(fileNameTextField.getText() + ".json");
        try {
            fileChooserDialog.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadButtonPressed () {
        System.out.println("Load button pressed");
        String selectedFileName = jsonFileListView.getSelectionModel().getSelectedItem().toString();
        System.out.println(selectedFileName);
        menu.setLoadLabelText(selectedFileName);
    }
}