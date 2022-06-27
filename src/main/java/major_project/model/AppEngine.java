package major_project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import major_project.model.parser.marvel.MarvelParser;
import major_project.model.parser.marvel.container.Container;
import major_project.model.parser.sendgrid.SendGridParser;
import major_project.model.parser.sendgrid.container.Email;

public class AppEngine {
    private MarvelParser marvelParser;
    private SendGridParser sendGridParser;
    
    private void checkDependenciesAreSet() {
        if (marvelParser == null || sendGridParser == null) {
            throw new IllegalStateException("At least one dependency has not been set");
        }
    }

    private void checkForNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    public MarvelParser marvelParser() {
        return marvelParser;
    }

    public SendGridParser sendGridParser() {
        return sendGridParser;
    }

    public void injectMarvelParser(MarvelParser marvelParser) {
        checkForNull(marvelParser);

        this.marvelParser = marvelParser;
    }

    public void injectSendGridParser(SendGridParser sendGridParser) {
        checkForNull(sendGridParser);

        this.sendGridParser = sendGridParser;
    }

    public List<String> getSuggestions(String input) {
        checkDependenciesAreSet();
        checkForNull(input);

        if (input.isEmpty() || input.isBlank()) {
            return new ArrayList<>();
        }

        return marvelParser.getCharacterNamesFromAPI(input);
    }

    public String getCharacterIDByName(String name) {
        checkDependenciesAreSet();
        checkForNull(name);
        
        String res = marvelParser.getCharacterIDByNameFromCache(name);
        if (res == null) {
            res = marvelParser.getCharacterIDByNameFromAPI(name);
        }

        return res;
    }

    public Container getCharacterByID(String id, boolean hitCache) {
        checkDependenciesAreSet();
        checkForNull(id);

        if (hitCache) {
            return marvelParser.getCharacterFromCache(id);
        } else {
            return marvelParser.getCharacterFromAPI(id);
        }
    }

    public Container getComicByID(String id, boolean hitCache) {
        checkDependenciesAreSet();
        checkForNull(id);

        if (hitCache) {
            return marvelParser.getComicFromCache(id);
        } else {
            return marvelParser.getComicFromAPI(id);
        }
    }

    public boolean sendReport(
        String toEmail,
        String toName,
        String fromEmail,
        String fromName,
        String subject,
        String content
    ) {
        checkDependenciesAreSet();
        for (String s : Arrays.asList(toEmail, toName, fromEmail, toEmail, subject, content)) {
            checkForNull(s);
        }

        Email email = new Email.EmailBuilder()
                .to(toEmail, toName)
                .from(fromEmail, fromName)
                .setSubject(subject)
                .setContent(content)
                .build();
                
        return sendGridParser.sendEmail(email);
    }
}
