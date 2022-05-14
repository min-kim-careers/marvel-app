package major_project.model.parser.marvel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import major_project.model.cache.CacheManager;
import major_project.model.parser.marvel.container.Character;
import major_project.model.parser.marvel.container.Comic;
import major_project.model.parser.marvel.container.Container;

public class OfflineMarvelParser implements MarvelParser {
    private final Gson gson = new Gson();
    private final CacheManager cacheManager;

    public OfflineMarvelParser() {
        cacheManager = new CacheManager("src\\main\\resources\\offline_cache.db");
    }

    @Override
    public List<String> getCharacterNamesFromAPI(String startsWith) {
        return cacheManager.characterCache().getCharacterNameListThatStartsWith(startsWith);
    }

    @Override
    public String getCharacterIDByName(String name) {
        return cacheManager.characterCache().getCharacterIDByName(name);
    }

    @Override
    public Container getCharacterFromAPI(String characterID) {
        return null;
    }

    @Override
    public Container getCharacterFromCache(String characterID) {
        try {
            Path p = Path.of("src\\main\\resources\\dummy_character.json");
            String characterJson = Files.readString(p);
            JsonObject jo = JsonParser.parseString(characterJson).getAsJsonObject();
            Container res = gson.fromJson(jo, Character.class);
            String imageUrl = ClassLoader.getSystemResource("character_image.jpg").toExternalForm();
            res.setImageUrl(imageUrl);
            return res;
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public Container getComicFromAPI(String comicID) {
        return null;
    }

    @Override
    public Container getComicFromCache(String comicID) {
        try {
            Path p = Path.of("src\\main\\resources\\dummy_comic.json");
            String comicJson = Files.readString(p);
            JsonObject jo = JsonParser.parseString(comicJson).getAsJsonObject();
            Container res = gson.fromJson(jo, Comic.class);
            String imageUrl = ClassLoader.getSystemResource("comic_image.jpg").toExternalForm();
            res.setImageUrl(imageUrl);
            return res;
        } catch (IOException e) {
        }
        return null;
    }

    @Override
    public void clearCache() {
    }

    @Override
    public boolean characterExists(String characterID) {
        return false;
    }

    @Override
    public boolean comicExists(String comicID) {
        return false;
    }
}