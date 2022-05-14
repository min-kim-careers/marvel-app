package major_project.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import major_project.model.parser.marvel.container.Container;
import major_project.model.parser.marvel.container.Item;

public class View extends HBox {
    private final Container container;
    private ListView<Item> listView;

    public View(Container container) {
        this.container = container;
        this.setAlignment(Pos.CENTER);
        this.setMinWidth(AppWindow.WIDTH);
        buildImageBox();
        buildInfoBox();
    }

    public String toString() {
        return container.getName();
    }

    public Container container() {
        return container;
    }

    public ListView<Item> listView() {
        return listView;
    }

    private void buildImageBox() {
        VBox imageBox = new VBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(25));

        Image image = new Image(container.getImageUrl());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(AppWindow.WIDTH * 0.25);
        imageView.setPreserveRatio(true);

        imageBox.getChildren().addAll(
            imageView
        );
        
        this.getChildren().add(imageBox);
    }

    private void buildInfoBox() {
        VBox infoBox = new VBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setPadding(new Insets(25));

        Label titleLabel = new Label(container.getName());
        titleLabel.setFont(new Font(30));
        titleLabel.setPadding(new Insets(25));

        Label listViewLabel = new Label("COLLECTION LIST");
        listViewLabel.setFont(new Font(20));

        listView = new ListView<>();
        listView.setPrefWidth(AppWindow.WIDTH * 0.4);

        listView.getItems().addAll(container.getItems());

        infoBox.getChildren().addAll(
            titleLabel,
            listViewLabel,
            listView
        );

        this.getChildren().add(infoBox);
    }
}
