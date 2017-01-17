package sample;/**
 * Created by Paul Dennis on 1/17/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class PaintWithFriends extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("paint-with-friends-menu.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

        Parent root = (Parent) fxmlLoader.load(location.openStream());
        MenuController controller = fxmlLoader.getController();
        controller.initializeBackRef(this);
        primaryStage.setTitle("Paint With Friends Main Menu");
        primaryStage.setScene(new Scene(root, 400, 275));
        primaryStage.show();
    }

    public void setLoadLabelText (String text) {
        MenuController controller = fxmlLoader.getController();
        controller.setFileNameLabel(text);
    }
}
