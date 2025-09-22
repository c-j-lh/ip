package ramarama;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {
    @FXML private Label text;
    @FXML private ImageView displayPicture;

    private DialogBox(String msg, Image img) {
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load(); // <-- this populates text & displayPicture
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        text.setText(msg);
        displayPicture.setImage(img);
    }

    public static DialogBox getUserDialog(String msg, Image img) {
        DialogBox db = new DialogBox(msg, img);
        db.flip(); // now safe
        return db;
    }

    public static DialogBox getRama2Dialog(String msg, Image img) {
        return new DialogBox(msg, img);
    }

    private void flip() {
        var nodes = javafx.collections.FXCollections.observableArrayList(getChildren());
        java.util.Collections.reverse(nodes);
        getChildren().setAll(nodes);
        setAlignment(javafx.geometry.Pos.TOP_RIGHT);
    }
}
