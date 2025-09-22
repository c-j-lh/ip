package ramarama;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launches application when called by Launcher.java.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Rama2 rama2 = new Rama2(Paths.get("data", "rama.txt").toString());
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

        javafx.scene.Parent root = fxml.load();

        MainWindow controller = fxml.getController(); // will NOT be null if fx:controller is set
        controller.setRama2(rama2);
        controller.greet();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/css/dialog-box.css").toExternalForm());

        stage.setTitle("Rama2");
        stage.setMinWidth(420);
        stage.setMinHeight(520);
        stage.setScene(scene);
        stage.show();
    }

}
