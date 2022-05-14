package major_project.view;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import major_project.controller.AppController;
import major_project.model.parser.marvel.OnlineMarvelParser;

public class AppWindow extends BorderPane {
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    
    private AppController controller;
    private Scene scene;
    private Viewer viewer;
    
    public AppWindow(AppController controller) {
        this.controller = controller;
        scene = new Scene(this, WIDTH, HEIGHT);
        viewer = new Viewer(controller);
        
        this.setTop(new NavBar(controller));
        this.setCenter(viewer);
    }

    public Viewer viewer() {
        return viewer;
    }

    public Scene scene() {
        return scene;
    }

    public boolean checkHitCache() {
        if (controller.model().marvelParser().getClass() == OnlineMarvelParser.class) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setHeaderText("Cache exists for this data? (OK to hit cache, CANCEL to request fresh data)");
            Optional<ButtonType> alert2 = alert1.showAndWait();
            if (alert2.get() == ButtonType.OK) {
                return true;
            } else {}
            return false;
        }
        return true;
    }
    
}
