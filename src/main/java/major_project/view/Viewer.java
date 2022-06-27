package major_project.view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import major_project.model.parser.marvel.OnlineMarvelParser;
import major_project.model.parser.marvel.container.Character;
import major_project.model.parser.marvel.container.Comic;
import major_project.model.parser.marvel.container.Container;
import major_project.model.parser.marvel.container.Item;

public class Viewer extends BorderPane {
    private final AppWindow window;
    private BreadCrumbBar breadCrumbBar;
    private View currentView;
    private int comicCountThreshold;

    public Viewer(AppWindow view) {
        this.window = view;
        initBreadCrumbBar();
    }

    private void initBreadCrumbBar() {
        breadCrumbBar = new BreadCrumbBar(window);
        this.setTop(breadCrumbBar);
    }

    public void setComicCountThreshold(int comicCountThreshold) {
        this.comicCountThreshold = comicCountThreshold;
    }

    public View getCurrentView() {
        return currentView;
    }

    public void setCurrentView(View view) {
        currentView = view;
        this.setCenter(view);
    }

    /**
     * If mouse is clicked twice, get the list element and set the new page with that element. If nothing is retrieved, end function.
     * @param event
     */
    private void viewListViewSetOnMouseClickedAction(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(event.getClickCount() == 2){
                ListView<?> lv = (ListView<?>) event.getSource();
                if (lv.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                String id = ((Item) lv.getSelectionModel().getSelectedItem()).getID();
                setPage(id, false);
            }
        }
    }

    /**
     * It will check whether the request is initiated from the search bar or from the collection list and search for the appropriate container.
     * Then the next new view page is set as the retrieved container while returning an alert error if nothing is retrieved.
     * Finally, gives a brief information about character popularity.
     * @param id
     * @param isSearching
     */
    public void setPage(String id, boolean isSearching) {
        Container container = null;
        if (window.model().marvelParser().getClass() == OnlineMarvelParser.class) {
            boolean hitCache = false;
            if (isSearching) {
                if (window.model().marvelParser().characterExists(id)) {
                    hitCache = checkCache();
                }
                container = window.model().getCharacterByID(id, hitCache);
            } else {
                if (currentView.container().getClass() == Character.class) {
                    if (window.model().marvelParser().comicExists(id)) {
                        hitCache = checkCache();
                    }
                    container = window.model().getComicByID(id, hitCache);
                } else if (currentView.container().getClass() == Comic.class) {
                    if (window.model().marvelParser().characterExists(id)) {
                        hitCache = checkCache();
                    }
                    container = window.model().getCharacterByID(id, hitCache);
                }
            }
        } else {
            if (isSearching) {
                container = window.model().getCharacterByID(id, true);
            } else {
                if (currentView.container().getClass() == Character.class) {
                    container = window.model().getComicByID(id, true);
                } else if (currentView.container().getClass() == Comic.class) {
                    container = window.model().getCharacterByID(id, true);
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

        checkCharacterIsPopular();
    }

    /**
     * Checks if the newly set container is a Character and raises an alert if its number of linked comic count is greater than the threshold entered on application startup.
     */
    public boolean checkCharacterIsPopular() {
        if (currentView.container().getClass() == Character.class && currentView.listView().getItems().size() > comicCountThreshold) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("This character is popular!");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Clears cache (removes all relevant tables from database)
     * @return true if confirmed
     */
    private boolean checkCache() {
        if (window.model().marvelParser().getClass() == OnlineMarvelParser.class) {
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
