package major_project.model.parser.marvel;

import java.util.List;

import major_project.model.parser.marvel.container.Container;

public interface MarvelParser {
    public String getCharacterIDByName(String name);

    public List<String> getCharacterNamesFromAPI(String startsWith);

    public Container getCharacterFromAPI(String characterID);
    public Container getCharacterFromCache(String characterID);

    public Container getComicFromAPI(String comicID);
    public Container getComicFromCache(String comicID);

    public boolean characterExists(String characterID);
    public boolean comicExists(String comicID);

    public void clearCache();

}
