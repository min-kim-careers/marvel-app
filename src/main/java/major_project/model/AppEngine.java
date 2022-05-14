package major_project.model;

import java.util.ArrayList;
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

    public MarvelParser marvelParser() {
        return marvelParser;
    }

    public SendGridParser sendGridParser() {
        return sendGridParser;
    }

    public void injectMarvelParser(MarvelParser marvelParser) {
        this.marvelParser = marvelParser;
    }

    public void injectSendGridParser(SendGridParser sendGridParser) {
        this.sendGridParser = sendGridParser;
    }

    public List<String> getSuggestions(String input) {
        checkDependenciesAreSet();

        if (input.isEmpty() || input.isBlank()) {
            return new ArrayList<>();
        }

        return marvelParser.getCharacterNamesFromAPI(input);
    }

    public String getCharacterIDByName(String name) {
        checkDependenciesAreSet();
        
        return marvelParser.getCharacterIDByName(name);
    }

    public Container getCharacterByID(String characterID, boolean hitCache) {
        checkDependenciesAreSet();

        if (hitCache) {
            return marvelParser.getCharacterFromCache(characterID);
        } else {
            return marvelParser.getCharacterFromAPI(characterID);
        }
    }

    public Container getComicByID(String comicID, boolean hitCache) {
        checkDependenciesAreSet();

        if (hitCache) {
            return marvelParser.getComicFromCache(comicID);
        } else {
            return marvelParser.getComicFromAPI(comicID);
        }
    }

    /**
     * Builds an email using the given parameters and sends the email by send grid parser.
     * @param toEmail
     * @param toName
     * @param fromEmail
     * @param fromName
     * @param subject
     * @param content
     * @return True if sending was successful, otherwise returns false.
     */
    public boolean sendReport(
        String toEmail,
        String toName,
        String fromEmail,
        String fromName,
        String subject,
        String content
    ) {
        checkDependenciesAreSet();

        Email email = new Email.EmailBuilder()
                .to(toEmail, toName)
                .from(fromEmail, fromName)
                .setSubject(subject)
                .setContent(content)
                .build();
                
        return sendGridParser.sendEmail(email);
    }
}
