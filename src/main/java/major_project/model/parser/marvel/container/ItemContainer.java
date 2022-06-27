package major_project.model.parser.marvel.container;

import java.util.List;

public class ItemContainer {
    List<Item> items;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item i : items) {
            sb.append(i + "\\n");
        }
        return sb.toString();
    }

    public List<Item> getItems() {
        return items;
    }
}
