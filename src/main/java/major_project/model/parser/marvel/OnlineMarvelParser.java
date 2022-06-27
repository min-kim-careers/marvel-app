package major_project.model.parser.marvel;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import major_project.model.Request;
import major_project.model.cache.CacheManager;
import major_project.model.parser.marvel.container.Character;
import major_project.model.parser.marvel.container.Comic;
import major_project.model.parser.marvel.container.Container;

public class OnlineMarvelParser implements MarvelParser {
    private final String hostUrl = "https://gateway.marvel.com";
    private final Gson gson = new Gson();
    private Request request;
    private CacheManager cacheManager;

    public void injectRequest(Request request) {
        this.request =  request;
    }

    public void injectCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private void checkDependenciesAreSet() {
        if (request == null || cacheManager == null) {
            throw new IllegalStateException();
        }
    }

    private void checkForNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    public CacheManager cacheManager() {
        return cacheManager;
    }

    /**
     * Makes sure results are retrieved until the last element in the database by incrementing the limit and offset parameters by 100 until the next response count is less than the offset.
     * @param url
     * @return List of JsonArrays from the json result key retrieved from the API or offline dummy.
     */
    private List<JsonArray> getResultArrays(String url) {
        checkDependenciesAreSet();
        checkForNull(url);
        
        int limit = 100;
        int offset = 0;
        List<JsonArray> ls = new ArrayList<>();
        while (true) {
            String tempUrl = url;
            if (url.contains("?")) {
                tempUrl += "&";
            } else {
                tempUrl += "?";
            }
            tempUrl += "limit=" + String.valueOf(limit) + "&offset=" + String.valueOf(offset) + "&" + MarvelAuthenticator.getAuthKey();
            JsonObject response = request.getResponse(tempUrl).getAsJsonObject().get("data").getAsJsonObject();
            if (response.get("count").getAsInt() < offset) {
                break;
            }
            JsonArray resultArray = response.get("results").getAsJsonArray();
            if (!resultArray.isEmpty()) {
                ls.add(resultArray);
            }
            offset += 100;
        }
        return ls;
    }

    /**
     * Iterates through all the json arrays from the response while adding all the name strings to a list as well as adding the character details to cache.
     * @return a list of names that start with the given parameter
     */
    @Override
    public List<String> getCharacterNamesFromAPI(String startsWith) {
        checkDependenciesAreSet();
        checkForNull(startsWith);

        startsWith = startsWith.replaceAll(" ", "%20");
        String url = hostUrl + "/v1/public/characters?nameStartsWith=" + startsWith;
        List<JsonArray> arrays = getResultArrays(url);
        if (arrays.isEmpty()) {
            return null;
        }
        List<String> res = new ArrayList<>();
        for (JsonArray ja : arrays) {
            for (int i = 0; i < ja.size(); i++) {
                JsonObject jo = ja.get(i).getAsJsonObject();
                String id = jo.get("id").getAsString();
                String name = jo.get("name").getAsString();
                res.add(name);
                cacheManager.characterCache().addCharacter(id, name, jo.toString());
            }
        }
        return res;
    }

    @Override
    public String getCharacterIDByNameFromCache(String name) {
        checkDependenciesAreSet();
        checkForNull(name);
        
        return cacheManager.characterCache().getCharacterIDByName(name);
    }

    @Override
    public String getCharacterIDByNameFromAPI(String name) {
        checkDependenciesAreSet();
        checkForNull(name);

        name = name.replaceAll(" ", "%20");
        String url = hostUrl + "/v1/public/characters?name=" + name;
        List<JsonArray> arrays = getResultArrays(url);
        if (arrays.isEmpty()) {
            return null;
        }
        JsonObject jo = arrays.get(0).get(0).getAsJsonObject();
        return jo.get("id").getAsString();
    }

    @Override
    public Container getCharacterFromAPI(String characterID) {
        checkDependenciesAreSet();
        checkForNull(characterID);
        
        String url = hostUrl + "/v1/public/characters/" + characterID;
        List<JsonArray> arrays = getResultArrays(url);
        if (arrays.isEmpty()) {
            return null;
        }
        JsonObject jo = arrays.get(0).get(0).getAsJsonObject();
        Container character = gson.fromJson(jo, Character.class);

        String id = jo.get("id").getAsString();
        String name = jo.get("name").getAsString();
        cacheManager.characterCache().addCharacter(id, name, jo.toString());

        return character;
    }

    @Override
    public Container getCharacterFromCache(String characterID) {
        checkDependenciesAreSet();
        checkForNull(characterID);
        
        String characterJson = cacheManager.characterCache().getCharacterJson(characterID);
        if (characterJson == null) {
            return null;
        }
        JsonObject jo = JsonParser.parseString(characterJson).getAsJsonObject();
        Container character = gson.fromJson(jo, Character.class);
        return character;
    }

    @Override
    public Container getComicFromAPI(String comicID) {
        checkDependenciesAreSet();
        checkForNull(comicID);
        
        String url = hostUrl + "/v1/public/comics/" + comicID;
        List<JsonArray> arrays = getResultArrays(url);
        if (arrays.isEmpty()) {
            return null;
        }
        JsonObject jo = arrays.get(0).get(0).getAsJsonObject();
        Container comic = gson.fromJson(jo, Comic.class);

        String id = jo.get("id").getAsString();
        cacheManager.comicCache().addComic(id, jo.toString());

        return comic;
    }

    @Override
    public Container getComicFromCache(String comicID) {
        checkDependenciesAreSet();
        checkForNull(comicID);
        
        String comicJson = cacheManager.comicCache().getComicJson(comicID);
        if (comicJson == null) {
            return null;
        }
        JsonObject jo = JsonParser.parseString(comicJson).getAsJsonObject();
        Container comic = gson.fromJson(jo, Comic.class);
        return comic;
    }

    @Override
    public void clearCache() {
        checkDependenciesAreSet();
        
        cacheManager.clearCache();
    }

    @Override
    public boolean characterExists(String characterID) {
        checkDependenciesAreSet();
        checkForNull(characterID);
        
        return cacheManager.characterCache().exists(characterID);
    }

    @Override
    public boolean comicExists(String comicID) {
        checkDependenciesAreSet();
        checkForNull(comicID);
        
        return cacheManager.comicCache().exists(comicID);
    }
}