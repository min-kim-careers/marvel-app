package major_project.model.parser.marvel.container;

import java.util.List;

public interface Container {
    public String getResourceURI();
    public String getName();
    public String getImageUrl();
    public String getEmailOutput();
    public List<Item> getItems();
    public void setImageUrl(String imageUrl);
}
