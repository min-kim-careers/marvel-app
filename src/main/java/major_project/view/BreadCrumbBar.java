package major_project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class BreadCrumbBar extends ScrollPane {
    private final AppWindow window;
    private HBox hbox;

    public BreadCrumbBar(AppWindow window) {
        this.window = window;

        hbox = new HBox();

        this.setContent(hbox);
        this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        this.setFitToHeight(true);
    }

    public void add(View view) {
        Button b = new Button(view.toString());
        b.setAlignment(Pos.CENTER);
        b.setFont(new Font(15));
        b.setPadding(new Insets(5));
        b.setStyle("-fx-border-color: black;");
        b.setOnMouseClicked((event) -> {
            window.viewer().setCurrentView(view);
        });

        hbox.getChildren().add(b);
    }
}
