package major_project.model.cache;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CacheManager {
    private final String dbName;
    private final String dbUrl;

    private final CharacterCache characterCache;
    private final ComicCache comicCache;
    
    public CacheManager(String dbName) {
        this.dbName = dbName;
        this.dbUrl = "jdbc:sqlite:" + dbName;
        initFile();
        characterCache = new CharacterCache(dbUrl);
        comicCache = new ComicCache(dbUrl);
    }

    private void initFile() {
        File f = new File(dbName);
        if (f.exists()) {
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbUrl)) {
        } catch (SQLException e) {
        }
    }

    public CharacterCache characterCache() {
        return characterCache;
    }

    public ComicCache comicCache() {
        return comicCache;
    }

    public void clearCache() {
        characterCache.clearCache();
        comicCache.clearCache();
    }
}
