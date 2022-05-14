package major_project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class About extends VBox {
    public About() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(30));
        initAlert();
    }

    private void initAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("""
            Application name: The Marvel Encyclopedia
            
            Author: Min Kim

            References:
                Background music - https://www.youtube.com/watch?v=_zxCX4rtuxs&ab_channel=PianoTutorial
        """);
        alert.showAndWait();
    }
}
