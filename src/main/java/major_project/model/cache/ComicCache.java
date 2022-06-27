package major_project.model.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComicCache {
    private String dbUrl;

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        createTable();
    }

    public void createTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS comics (
                    id INTEGER PRIMARY KEY,
                    comic_id TEXT NOT NULL UNIQUE,
                    json TEXT
                );
                """;
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            statement.execute(query);
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
    }

    public void addComic(String comicID, String json) {
        String sqlComicID = comicID.replace("'", "''");
        String sqlJson = json.replace("'", "''");
        String query = String.format("""
                    INSERT INTO comics(comic_id, json) VALUES('%s', '%s');
                """, sqlComicID, sqlJson);
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            statement.execute(query);
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
    }

    public String getComicJson(String comicID) {
        String query = String.format("""
                    SELECT json as json
                    FROM comics
                    WHERE comic_id='%s';
                """, comicID);
        String res = null;
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            ResultSet set = statement.executeQuery(query);
            res = set.getString("json");
            set.close();
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
        return res;
    }

    public void clearCache() {
        String query = """
                DROP TABLE IF EXISTS comics;
                """;
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            statement.execute(query);
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
    }

    public boolean exists(String comicID) {
        String query = String.format("""
                    SELECT COUNT(*) as count
                    FROM comics
                    WHERE comic_id='%s';
                """, comicID);
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            ResultSet set = statement.executeQuery(query);
            int res = set.getInt("count");
            set.close();
            conn.close();
            statement.close();
            if (res > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
}
