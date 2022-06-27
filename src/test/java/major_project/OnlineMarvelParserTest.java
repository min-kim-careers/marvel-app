package major_project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import major_project.model.Request;
import major_project.model.cache.CacheManager;
import major_project.model.cache.CharacterCache;
import major_project.model.cache.ComicCache;
import major_project.model.parser.marvel.MarvelParser;
import major_project.model.parser.marvel.OnlineMarvelParser;
import major_project.model.parser.marvel.container.Container;

public class OnlineMarvelParserTest {
    private MarvelParser fixture;
    private Request request;
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        request = mock(Request.class);
        cacheManager = mock(CacheManager.class);
        fixture = new OnlineMarvelParser();
        fixture.injectRequest(request);
        fixture.injectCacheManager(cacheManager);

        when(cacheManager.characterCache()).thenReturn(mock(CharacterCache.class));
        when(cacheManager.comicCache()).thenReturn(mock(ComicCache.class));
    }

    @AfterEach
    public void tearDown() {
        request = null;
        cacheManager = null;
        fixture = null;
    }

    private JsonObject getFileAsJsonObject(String filename) {
        try {
            URI uri = ClassLoader.getSystemResource(filename).toURI();
            String s = new String(Files.readAllBytes(Paths.get(uri)));
            return JsonParser.parseString(s).getAsJsonObject() ;
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JsonArray getResultArray(JsonObject jo) {
        return jo.get("data").getAsJsonObject().get("results").getAsJsonArray();
    }

    @Test
    public void testGetCharacterNamesFromAPI() {
        JsonObject jo = getFileAsJsonObject("character_list.json");
        when(request.getResponse(any())).thenReturn(jo);
        List<String> actual = fixture.getCharacterNamesFromAPI(anyString());
        List<String> expected = new ArrayList<>();
        for (JsonElement e : getResultArray(jo)) {
            expected.add(e.getAsJsonObject().get("name").getAsString());
        }
        assertThat(actual, equalTo(expected));
    }


    @Test
    public void testGetCharacterIDByNameForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterIDByNameFromAPI(null));
    }   

    @Test
    public void testGetCharacterIDByNameFromAPIForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterIDByNameFromAPI(null));
    }   

    @Test
    public void testGetCharacterFromAPIForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterFromAPI(null));
    }   

    @Test
    public void testGetCharacterFromCacheForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterFromCache(null));
    }   

    @Test
    public void testGetComicFromAPIForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getComicFromAPI(null));
    }   

    @Test
    public void testGetComicFromCacheForNull() {
        assertThrows(NullPointerException.class, () -> fixture.getComicFromCache(null));
    }   

    @Test
    public void testCharacterExistsForNull() {
        assertThrows(NullPointerException.class, () -> fixture.characterExists(null));
    }   

    @Test
    public void testComicExistsForNull() {
        assertThrows(NullPointerException.class, () -> fixture.comicExists(null));
    }    


    @Test
    public void testGetCharacterIDByName() {
        when(cacheManager.characterCache().getCharacterIDByName("Spiderman")).thenReturn("123456");
        assertThat(fixture.getCharacterIDByNameFromCache("Spiderman"), equalTo("123456"));
    }

    @Test
    public void testGetCharacterIDByNameFromAPI() {
        JsonObject jo = getFileAsJsonObject("test_character.json");
        when(request.getResponse(anyString())).thenReturn(jo);
        String actual = fixture.getCharacterIDByNameFromAPI(anyString());
        String expected = "1011334";
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testGetCharacterFromAPI() {
        JsonObject jo = getFileAsJsonObject("test_character.json");
        when(request.getResponse(anyString())).thenReturn(jo);
        Container actual = fixture.getCharacterFromAPI(anyString());
        String expected = "Dummy Character";
        assertThat(actual.getName(), equalTo(expected));
    }

    @Test
    public void testGetCharacterFromCache() {
        JsonObject jo = getResultArray(getFileAsJsonObject("test_character.json")).get(0).getAsJsonObject();
        when(cacheManager.characterCache().getCharacterJson(anyString())).thenReturn(jo.toString());
        Container actual = fixture.getCharacterFromCache("1011334");
        String expected = "Dummy Character";
        assertThat(actual.getName(), equalTo(expected));
    }

    @Test
    public void testGetComicFromAPI() {
        JsonObject jo = getFileAsJsonObject("test_comic.json");
        when(request.getResponse(anyString())).thenReturn(jo);
        Container actual = fixture.getComicFromAPI(anyString());
        String expected = "Dummy Comic";
        assertThat(actual.getName(), equalTo(expected));
    }

    @Test
    public void testGetComicFromCache() {
        JsonObject jo = getResultArray(getFileAsJsonObject("test_comic.json")).get(0).getAsJsonObject();
        when(cacheManager.comicCache().getComicJson(anyString())).thenReturn(jo.toString());
        Container actual = fixture.getComicFromCache("1011334");
        String expected = "Dummy Comic";
        assertThat(actual.getName(), equalTo(expected));
    }

    @Test
    public void testClearCache() {
        JsonObject jo = getFileAsJsonObject("test_comic.json");
        when(request.getResponse(anyString())).thenReturn(jo);
        jo = getResultArray(getFileAsJsonObject("test_comic.json")).get(0).getAsJsonObject();
        when(cacheManager.comicCache().getComicJson(anyString())).thenReturn(jo.toString());
        fixture.getComicFromAPI(anyString());
        Container actual = fixture.getComicFromCache("1011334");
        assertThat(actual.getName(), equalTo("Dummy Comic"));
        fixture.clearCache();
        when(cacheManager.comicCache().getComicJson(anyString())).thenReturn(null);
        assertThat(fixture.getComicFromCache("1011334"), equalTo(null));
    }

    @Test
    public void testCharacterExists() {
        when(cacheManager.characterCache().exists("some string")).thenReturn(true);
        assertThat(fixture.characterExists("some string"), equalTo(true));
    }

    @Test
    public void testComicExists() {
        when(cacheManager.comicCache().exists("some string")).thenReturn(true);
        assertThat(fixture.comicExists("some string"), equalTo(true));
    }
}
