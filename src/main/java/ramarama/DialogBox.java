package ramarama;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Java component for a single chat bubble with an avatar.
 */
// only the changed/added lines shown
public class DialogBox extends HBox {
    @FXML private Label dialog;
    @FXML private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxml = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxml.setController(this);
            fxml.setRoot(this);
            fxml.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // NEW: style classes (default = user bubble on the right)
        this.getStyleClass().add("dialog");
        dialog.getStyleClass().addAll("bubble", "user");

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /** Bot on the left. */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);

        // Switch bubble style from user -> bot
        dialog.getStyleClass().remove("user");
        if (!dialog.getStyleClass().contains("bot")) {
            dialog.getStyleClass().add("bot");
        }
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getRama2Dialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

