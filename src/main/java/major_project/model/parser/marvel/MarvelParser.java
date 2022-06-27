package major_project.model.parser.marvel;

import java.util.List;

import major_project.model.Request;
import major_project.model.cache.CacheManager;
import major_project.model.parser.marvel.container.Container;

public interface MarvelParser {
    public CacheManager cacheManager();

    public void injectRequest(Request request);
    public void injectCacheManager(CacheManager cacheManager);

    public String getCharacterIDByNameFromCache(String name);
    public String getCharacterIDByNameFromAPI(String name);

    public List<String> getCharacterNamesFromAPI(String startsWith);

    public Container getCharacterFromAPI(String characterID);
    public Container getCharacterFromCache(String characterID);

    public Container getComicFromAPI(String comicID);
    public Container getComicFromCache(String comicID);

    public boolean characterExists(String characterID);
    public boolean comicExists(String comicID);

    public void clearCache();

}
