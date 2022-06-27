package major_project.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import major_project.model.AppEngine;

public class AppWindow extends BorderPane {
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    
    private AppEngine model;
    private Scene scene;
    private Viewer viewer;
    
    public AppWindow(AppEngine model) {
        this.model = model;

        scene = new Scene(this, WIDTH, HEIGHT);
        viewer = new Viewer(this);
        
        this.setTop(new NavBar(this));
        this.setCenter(viewer);
    }

    public Viewer viewer() {
        return viewer;
    }

    public Scene scene() {
        return scene;
    }

    public AppEngine model() {
        return model;
    }
}
