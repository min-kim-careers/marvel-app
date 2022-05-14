package major_project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import major_project.controller.AppController;

public class BreadCrumbBar extends ScrollPane {
    private final AppController controller;
    private HBox hbox;

    public BreadCrumbBar(AppController controller) {
        this.controller = controller;

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
            controller.view().viewer().setCurrentView(view);
        });

        hbox.getChildren().add(b);
    }
}
