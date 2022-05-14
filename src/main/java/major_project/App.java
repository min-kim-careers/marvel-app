package major_project;

import javafx.application.Application;
import javafx.stage.Stage;
import major_project.controller.AppController;
import major_project.model.parser.marvel.MarvelParser;
import major_project.model.parser.marvel.OfflineMarvelParser;
import major_project.model.parser.marvel.OnlineMarvelParser;
import major_project.model.parser.sendgrid.OfflineSendGridParser;
import major_project.model.parser.sendgrid.OnlineSendGridParser;
import major_project.model.parser.sendgrid.SendGridParser;

public class App extends Application {
    private final static AppController controller = new AppController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(controller.view().scene());
        primaryStage.setTitle("The Marvel Encyclopedia");
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        MarvelParser marvelParser = null;
        if (args[0].equalsIgnoreCase("online")) {
            marvelParser = new OnlineMarvelParser();
        } else if (args[0].equalsIgnoreCase("offline")) {
            marvelParser = new OfflineMarvelParser();
        } else {
            throw new IllegalArgumentException("First gradle argument was incorrect");
        }
        controller.model().injectMarvelParser(marvelParser);

        SendGridParser sendGridParser = null;
        if (args[1].equalsIgnoreCase("online")) {
            sendGridParser = new OnlineSendGridParser();
        } else if (args[1].equalsIgnoreCase("offline")) {
            sendGridParser = new OfflineSendGridParser();
        } else {
            throw new IllegalArgumentException("Second gradle argument was incorrect");
        }
        controller.model().injectSendGridParser(sendGridParser);

        launch();
    }
}