package sample;/**
 * Created by Paul Dennis on 1/17/2017.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileChooserDialog extends Application {

    boolean loading;
    PaintCanvasNetwork app;
    FXMLLoader fxmlLoader;

    PaintWithFriends menu;

    public FileChooserDialog (boolean loading, PaintCanvasNetwork pcn, PaintWithFriends menu) {
        super();
        this.loading = loading;
        app = pcn;
        this.menu = menu;
    }

    public FileChooserDialog () {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("file-chooser-dialog.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        FileChooserController controller = fxmlLoader.getController();
        controller.setContextInfo(loading, app, this, menu);
        primaryStage.setTitle("File Chooser");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        /*Group rootGroup = new Group();

        ListView jsonFileList = new ListView();
        rootGroup.getChildren().add(jsonFileList);

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

        jsonFileList.setItems(jsonFileNames);

        Scene scene = new Scene(rootGroup, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

}
