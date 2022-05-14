package major_project.model.parser.marvel.container;

import java.util.List;

public class Character implements Container {
    private String name;
    private String resourceURI;
    private Thumbnail thumbnail;
    private ItemContainer comics;
    private ItemContainer stories;
    private ItemContainer events;
    
    private String imageUrl;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
        if (imageUrl == null) {
            return thumbnail.getPath() + "/portrait_incredible." + thumbnail.getExtension();
        }
        return imageUrl;
    }

    @Override
    public List<Item> getItems() {
        return comics.getItems();
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getEmailOutput() {
        String res = "Name:\\n%s\\n\\nComics:\\n%s\\nStories:\\n%s\\nEvents:\\n%s";
        return String.format(res, name, comics, stories, events);
    }

    @Override
    public String getResourceURI() {
        return resourceURI;
    }
}