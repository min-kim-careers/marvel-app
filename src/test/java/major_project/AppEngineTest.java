// package major_project;

// import static org.hamcrest.CoreMatchers.nullValue;
// import static org.hamcrest.MatcherAssert.assertThat;
// import static org.hamcrest.Matchers.containsInAnyOrder;
// import static org.hamcrest.Matchers.equalTo;
// import static org.hamcrest.Matchers.is;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import javafx.util.Pair;
// import major_project.model.AppEngine;
// import major_project.model.marvelparser.MarvelParser;
// import major_project.model.marvelparser.OfflineMarvelParser;
// import major_project.model.marvelparser.container.Container;
// import major_project.model.sendgridparser.OfflineSendGridParser;
// import major_project.model.sendgridparser.SendGridParser;

// public class AppEngineTest {
//     private AppEngine fixture;
//     private MarvelParser marvelParser;
//     private SendGridParser sendGridParser;

//     private String[] sampleNameArray = "A-Bomb (HAS),A.I.M.,Aaron Stack,Abomination (Emil Blonsky),Abomination (Ultimate),Absorbing Man,Abyss,Abyss (Age of Apocalypse),Adam Destine,Adam Warlock,Aegis (Trey Rollins),Aero (Aero),Agatha Harkness,Agent Brand,Agent X (Nijo),Agent Zero,Agents of Atlas,Aginar,Air-Walker (Gabriel Lan)".split(",");

//     private void injectParsers() {
//         fixture.injectMarvelParser(marvelParser);
//         fixture.injectSendGridParser(sendGridParser);
//     }

//     private List<Pair<String, String>> getSuggestionList() {
//         List<Pair<String, String>> res = new ArrayList<>();
//         for (String s : Arrays.asList(sampleNameArray)) {
//             res.add(new Pair<String,String>(String.valueOf(res.size()), s));
//         }
//         return res;
//     }

//     @BeforeEach
//     public void setUp() {
//         marvelParser = mock(OfflineMarvelParser.class);
//         sendGridParser = mock(OfflineSendGridParser.class);
//         fixture = new AppEngine();
//     }

//     @AfterEach
//     public void tearDown() {
//         fixture = null;
//         marvelParser = null;
//         sendGridParser = null;
//     }

//     @Test
//     public void testSuggestionsForEmptyInput() {
//         when(marvelParser.getAllCharacterNames()).thenReturn(new ArrayList<>());
//         injectParsers();
//         List<String> suggestions = fixture.getSuggestions("");
//         assertThat(suggestions, containsInAnyOrder(new String[0]));
//     }

//     @Test
//     public void testSuggestionsForSingleLetter() {
//         when(marvelParser.getAllCharacterNames()).thenReturn(getSuggestionList());
//         injectParsers();
//         List<String> suggestions = fixture.getSuggestions("a");
//         assertThat(suggestions, containsInAnyOrder(sampleNameArray));
//     }

//     @Test
//     public void testSuggestionsForSpecificCharacter() {
//         when(marvelParser.getAllCharacterNames()).thenReturn(getSuggestionList());
//         injectParsers();
//         List<String> suggestions = fixture.getSuggestions("Absorbing Man");
//         assertThat(suggestions, containsInAnyOrder(new String[] {"Absorbing Man"}));
//     }

//     @Test
//     public void testGetCharacterIDByName() {
//         when(marvelParser.getAllCharacterNames()).thenReturn(getSuggestionList());
//         injectParsers();
//         String actual = fixture.getCharacterIDByName("Absorbing Man");
//         String expected = String.valueOf(Arrays.asList(sampleNameArray).indexOf("Absorbing Man"));
//         assertThat(actual, equalTo(expected));

//         actual = fixture.getCharacterIDByName("Name that doesn't exist");
//         assertThat(actual, is(nullValue()));
//     }

//     @Test
//     public void testSendReport() {
//         injectParsers();
//         when(sendGridParser.sendEmail(any())).thenReturn(true);
//         assertThat(fixture.sendReport(
//             "toEmail", 
//             "toName", 
//             "fromEmail", 
//             "fromName", 
//             "subject", 
//             "content"
//         ), equalTo(true));
//     }

//     @Test
//     public void testGetCharacterByID() {
//         injectParsers();
//         Container mockContainer = mock(Container.class);
//         when(mockContainer.getName()).thenReturn("Some name");
//         when(marvelParser.getCharacterFromAPI(any())).thenReturn(mockContainer);
//         String actual = fixture.getCharacterByID("Some name").getName();
//         String expected = mockContainer.getName();
//         assertThat(actual, equalTo(expected));
//     }
// }