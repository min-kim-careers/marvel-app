package major_project.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import major_project.controller.AppController;
import major_project.model.parser.marvel.OnlineMarvelParser;
import major_project.model.parser.marvel.container.Character;
import major_project.model.parser.marvel.container.Comic;
import major_project.model.parser.marvel.container.Container;
import major_project.model.parser.marvel.container.Item;

public class Viewer extends BorderPane {
    private final AppController controller;
    private BreadCrumbBar breadCrumbBar;
    private View currentView;

    public Viewer(AppController controller) {
        this.controller = controller;
        initBreadCrumbBar();
    }

    private void initBreadCrumbBar() {
        breadCrumbBar = new BreadCrumbBar(controller);
        this.setTop(breadCrumbBar);
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View view) {
        currentView = view;
        this.setCenter(view);
    }

    private void viewListViewSetOnMouseClickedAction(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                @SuppressWarnings("unchecked")
                ListView<Item> lv = (ListView<Item>) event.getSource();
                if (lv.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                String id = lv.getSelectionModel().getSelectedItem().getID();
                setPage(id, false);
            }
        }
    }

    public void setPage(String id, boolean isSearching) {
        Container container = null;
        if (controller.model().marvelParser().getClass() == OnlineMarvelParser.class) {
            boolean hitCache = false;
            if (isSearching) {
                if (controller.model().marvelParser().characterExists(id)) {
                    hitCache = controller.view().checkHitCache();
                }
                container = controller.model().getCharacterByID(id, hitCache);
            } else {
                if (currentView.container().getClass() == Character.class) {
                    if (controller.model().marvelParser().comicExists(id)) {
                        hitCache = controller.view().checkHitCache();
                    }
                    container = controller.model().getComicByID(id, hitCache);
                } else if (currentView.container().getClass() == Comic.class) {
                    if (controller.model().marvelParser().characterExists(id)) {
                        hitCache = controller.view().checkHitCache();
                    }
                    container = controller.model().getCharacterByID(id, hitCache);
                }
            }
        } else {
            if (isSearching) {
                container = controller.model().getCharacterByID(id, true);
            } else {
                if (currentView.container().getClass() == Character.class) {
                    container = controller.model().getComicByID(id, true);
                } else if (currentView.container().getClass() == Comic.class) {
                    container = controller.model().getCharacterByID(id, true);
                }
            }
        }

        if (container == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing to retrieve. Aborting request.");
            alert.show();
            return;
        }
        
        View view = new View(container);
        view.listView().setOnMouseClicked((e) -> viewListViewSetOnMouseClickedAction(e));
        currentView = view;
        breadCrumbBar.add(view);
        this.setCenter(view);
    }

    
}
