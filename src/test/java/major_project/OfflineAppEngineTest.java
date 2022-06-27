package major_project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import major_project.model.AppEngine;
import major_project.model.parser.marvel.MarvelParser;
import major_project.model.parser.marvel.OfflineMarvelParser;
import major_project.model.parser.marvel.container.Container;
import major_project.model.parser.sendgrid.OfflineSendGridParser;
import major_project.model.parser.sendgrid.SendGridParser;


public class OfflineAppEngineTest {
    private AppEngine fixture;
    private MarvelParser marvelParser;
    private SendGridParser sendGridParser;

    @BeforeEach
    public void setUp() {
        marvelParser = mock(OfflineMarvelParser.class);
        sendGridParser = mock(OfflineSendGridParser.class);
        fixture = new AppEngine();
        fixture.injectMarvelParser(marvelParser);
        fixture.injectSendGridParser(sendGridParser);
    }

    @AfterEach
    public void tearDown() {
        fixture = null;
        marvelParser = null;
        sendGridParser = null;
    }

    @Test
    public void testForNullDependencyInjections() {
        assertThrows(NullPointerException.class, () -> fixture.injectMarvelParser(null));
        assertThrows(NullPointerException.class, () -> fixture.injectSendGridParser(null));
    }

    @Test
    public void testGetSuggestionsForNullException() {
        assertThrows(NullPointerException.class, () -> fixture.getSuggestions(null));
    }

    @Test
    public void testGetCharacterIDByNameForNullException() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterIDByName(null));
    }

    @Test
    public void testCharacterByIDForNullException() {
        assertThrows(NullPointerException.class, () -> fixture.getCharacterByID(null, true));
        assertThrows(NullPointerException.class, () -> fixture.getCharacterByID(null, false));
    }

    @Test
    public void testGetComicByIDForNullException() {
        assertThrows(NullPointerException.class, () -> fixture.getComicByID(null, true));
        assertThrows(NullPointerException.class, () -> fixture.getComicByID(null, false));
    }

    @Test
    public void testSendReportForNullException() {
        assertThrows(NullPointerException.class, () -> fixture.sendReport(null, null, null, null, null, null));
    }

    @Test
    public void testGetSuggestions() {
        when(marvelParser.getCharacterNamesFromAPI("spi")).thenReturn(Arrays.asList("Spiderman"));
        assertThat(fixture.getSuggestions("spi"), equalTo(Arrays.asList("Spiderman")));
    }

    @Test
    public void testGetCharacterIDByName() {
        when(marvelParser.getCharacterIDByNameFromCache("spiderman")).thenReturn("123456");
        assertThat(fixture.getCharacterIDByName("spiderman"), equalTo("123456"));
    }

    @Test
    public void testGetCharacterByIDFromAPI() {
        Container mock = mock(Container.class);
        when(marvelParser.getCharacterFromAPI("123456")).thenReturn(mock);
        assertThat(fixture.getCharacterByID("123456", false), equalTo(mock));
    }

    @Test
    public void testGetCharacterByIDFromCache() {
        Container mock = mock(Container.class);
        when(marvelParser.getCharacterFromCache("123456")).thenReturn(mock);
        assertThat(fixture.getCharacterByID("123456", true), equalTo(mock));
    }

    @Test
    public void testGetComicByIDFromAPI() {
        Container mock = mock(Container.class);
        when(marvelParser.getComicFromAPI("123456")).thenReturn(mock);
        assertThat(fixture.getComicByID("123456", false), equalTo(mock));
    }

    @Test
    public void testGetComicByIDFromCache() {
        Container mock = mock(Container.class);
        when(marvelParser.getComicFromCache("123456")).thenReturn(mock);
        assertThat(fixture.getComicByID("123456", true), equalTo(mock));
    }

    @Test
    public void testSendReport() {
        when(sendGridParser.sendEmail(any())).thenReturn(true);
        assertThat(fixture.sendReport("toEmail", "toName", "fromEmail", "fromName", "subject", "content"), equalTo(true));
    }
}
