package ramarama;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class MainWindow {
    @FXML private ScrollPane scrollPane;
    @FXML private VBox dialogContainer;
    @FXML private TextField input;
    @FXML private Button sendButton;

    private Rama2 rama2;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image rama2Image = new Image(this.getClass().getResourceAsStream("/images/rama2.jpg"));

    void setRama2(Rama2 rama2) {
        this.rama2 = rama2;
    }

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        input.setOnAction(e -> handleSend());
        sendButton.setOnAction(e -> handleSend());
    }

    void greet() {
        dialogContainer.getChildren().add(
            DialogBox.getRama2Dialog("Hello! I'm Rama2\nWhat can I do for you?\n", rama2Image)
        );
    }

    @FXML
    private void handleSend() {
        String text = input.getText();
        if (text == null || text.isBlank()) return;

        dialogContainer.getChildren().add(
            DialogBox.getUserDialog(text, userImage)
        );
        String reply = rama2.getResponse(text);
        dialogContainer.getChildren().add(
            DialogBox.getRama2Dialog(reply, rama2Image)
        );

        input.clear();
        if ("bye".equalsIgnoreCase(text.trim())) {
            Platform.exit();
        }
    }
}
