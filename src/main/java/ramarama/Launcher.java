package ramarama;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        System.out.println("jhj\n\n\n");
        System.out.println("hi world");
        System.out.println("FXML URL = " + Main.class.getResource("/view/MainWindow.fxml"));
        Application.launch(Main.class, args);
    }
}
