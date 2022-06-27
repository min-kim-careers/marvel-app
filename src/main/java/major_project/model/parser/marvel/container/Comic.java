package major_project.model.parser.marvel.container;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Comic implements Container {
    @SerializedName("title")
    private String name;
    private String resourceURI;
    private Thumbnail thumbnail;
    private ItemContainer characters;
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
        return characters.getItems();
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String getEmailOutput() {
        String res = "Name:\\n%s\\n\\nComics:\\n%s\\nStories:\\n%s\\nEvents:\\n%s";
        return String.format(res, name, characters, stories, events);
    }

    @Override
    public String getResourceURI() {
        return resourceURI;
    }
}