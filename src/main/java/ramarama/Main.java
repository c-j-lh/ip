package ramarama;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Launched by Launcher.java and sets up GUI to run.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Rama2 rama2 = new Rama2(Paths.get("data", "rama.txt").toString());
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));

        assert fxml != null;

        Scene scene = new Scene(fxml.load());
        MainWindow controller = fxml.getController();
        controller.setRama2(rama2);
        stage.setTitle("Rama2");
        stage.setScene(scene);
        stage.show();
        controller.greet();
    }
}
