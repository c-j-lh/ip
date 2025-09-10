package ramarama;

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("jh\n\n\n");
        Rama2 rama2 = new Rama2(Paths.get("data", "rama.txt").toString());
        System.out.println("FXML URL = " + Main.class.getResource("/view/MainWindow.fxml"));
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
