package major_project.controller;

import major_project.model.AppEngine;
import major_project.view.AppWindow;

public class AppController {
    private final AppEngine model;
    private final AppWindow view;

    public AppController() {
        this.model = new AppEngine();
        this.view = new AppWindow(this);
    }

    public AppEngine model() {
        return model;
    }

    public AppWindow view() {
        return view;
    }
}
