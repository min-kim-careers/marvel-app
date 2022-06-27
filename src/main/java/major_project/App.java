package major_project;

import javafx.application.Application;
import javafx.stage.Stage;
import major_project.model.AppEngine;
import major_project.model.Request;
import major_project.model.cache.CacheManager;
import major_project.model.parser.marvel.MarvelParser;
import major_project.model.parser.marvel.OfflineMarvelParser;
import major_project.model.parser.marvel.OnlineMarvelParser;
import major_project.model.parser.sendgrid.OfflineSendGridParser;
import major_project.model.parser.sendgrid.OnlineSendGridParser;
import major_project.model.parser.sendgrid.SendGridParser;
import major_project.view.AppWindow;
import major_project.view.PopularityChecker;

public class App extends Application {
    private static AppEngine model = new AppEngine();
    private static AppWindow view =  new AppWindow(model);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(view.scene());
        primaryStage.setTitle("The Marvel Encyclopedia");

        primaryStage.show();

        view.viewer().setComicCountThreshold(new PopularityChecker().getComicCountThreshold());
    }

    public static void main(String[] args) {
        String onlineCacheDir = App.class.getResource("/online_cache.db").toExternalForm();
        String offlineCacheDir = App.class.getResource("/offline_cache.db").toExternalForm();


        MarvelParser marvelParser = null;
        if (args[0].equalsIgnoreCase("online")) {
            marvelParser = new OnlineMarvelParser();
            marvelParser.injectRequest(new Request());
            marvelParser.injectCacheManager(new CacheManager(onlineCacheDir));
        } else if (args[0].equalsIgnoreCase("offline")) {
            marvelParser = new OfflineMarvelParser();
            marvelParser.injectCacheManager(new CacheManager(offlineCacheDir));
        } else {
            throw new IllegalArgumentException("First gradle argument is incorrect");
        }
        model.injectMarvelParser(marvelParser);

        SendGridParser sendGridParser = null;
        if (args[1].equalsIgnoreCase("online")) {
            sendGridParser = new OnlineSendGridParser();
        } else if (args[1].equalsIgnoreCase("offline")) {
            sendGridParser = new OfflineSendGridParser();
        } else {
            throw new IllegalArgumentException("Second gradle argument is incorrect");
        }
        model.injectSendGridParser(sendGridParser);

        launch();
    }
}