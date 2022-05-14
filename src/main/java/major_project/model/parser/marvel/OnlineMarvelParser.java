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
    private final Request request = new Request();
    private final CacheManager cacheManager;

    public OnlineMarvelParser() {
        cacheManager = new CacheManager("src\\main\\resources\\online_cache.db");
    }

    private List<JsonArray> getResultArrays(String url) {
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

            if (response.get("total").getAsInt() < offset) {
                break;
            }

            JsonArray resultArray = response.get("results").getAsJsonArray();
            ls.add(resultArray);
            offset += 100;
        }
        return ls;
    }

    @Override
    public List<String> getCharacterNamesFromAPI(String startsWith) {
        String url = hostUrl + "/v1/public/characters?nameStartsWith=" + startsWith;
        List<JsonArray> arrays = getResultArrays(url);
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
    public String getCharacterIDByName(String name) {
        return cacheManager.characterCache().getCharacterIDByName(name);
    }

    @Override
    public Container getCharacterFromAPI(String characterID) {
        String url = hostUrl + "/v1/public/characters/" + characterID;
        List<JsonArray> arrays = getResultArrays(url);
        JsonObject jo = arrays.get(0).get(0).getAsJsonObject();
        Container character = gson.fromJson(jo, Character.class);

        String id = jo.get("id").getAsString();
        String name = jo.get("name").getAsString();
        cacheManager.characterCache().addCharacter(id, name, jo.toString());

        return character;
    }

    @Override
    public Container getCharacterFromCache(String characterID) {
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
        String url = hostUrl + "/v1/public/comics/" + comicID;
        List<JsonArray> arrays = getResultArrays(url);
        JsonObject jo = arrays.get(0).get(0).getAsJsonObject();
        Container comic = gson.fromJson(jo, Comic.class);

        String id = jo.get("id").getAsString();
        cacheManager.comicCache().addComic(id, jo.toString());

        return comic;
    }

    @Override
    public Container getComicFromCache(String comicID) {
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
        cacheManager.clearCache();
    }

    @Override
    public boolean characterExists(String characterID) {
        return cacheManager.characterCache().exists(characterID);
    }

    @Override
    public boolean comicExists(String comicID) {
        return cacheManager.comicCache().exists(comicID);
    }
}