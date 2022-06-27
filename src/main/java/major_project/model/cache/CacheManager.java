package major_project.model.cache;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CacheManager {
    private final String dbName;
    private final String dbUrl;
    private final CharacterCache characterCache = new CharacterCache();
    private final ComicCache comicCache = new ComicCache();
    
    public CacheManager(String dbDir) {
        this.dbName = dbDir;
        this.dbUrl = "jdbc:sqlite:" + dbDir;
        initFile();
        characterCache.setDbUrl(dbUrl);
        comicCache.setDbUrl(dbUrl);
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
        characterCache.createTable();

        comicCache.clearCache();
        comicCache.createTable();
    }
}
