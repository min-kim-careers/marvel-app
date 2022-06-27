package major_project.model.cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CharacterCache {
    private String dbUrl;

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        createTable();
    }

    public void createTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS characters (
                    id INTEGER PRIMARY KEY,
                    character_id TEXT NOT NULL UNIQUE,
                    name TEXT NOT NULL,
                    json TEXT NOT NULL
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

    public void addCharacter(String characterID, String name, String json) {
        String sqlCharacterID = characterID.replace("'", "''");
        String sqlName = name.replace("'", "''");
        String sqlJson = json.replace("'", "''");
        String query = String.format("""
                    INSERT INTO characters(character_id, name, json) VALUES('%s', '%s', '%s');
                """, sqlCharacterID, sqlName, sqlJson);
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

    public List<String> getCharacterNameListThatStartsWith(String startsWith) {
        String query = String.format("""
                    SELECT name as name
                    FROM characters
                    WHERE name LIKE '%s%%';
                """, startsWith);
        List<String> res = new ArrayList<>();
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            ResultSet set = statement.executeQuery(query);
            while (set.next()) {
                res.add(set.getString("name"));
            }
            set.close();
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
        return res;
    }

    public String getCharacterJson(String characterID) {
        String query = String.format("""
                    SELECT json as json
                    FROM characters
                    WHERE character_id='%s';
                """, characterID);
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

    public String getCharacterIDByName(String name) {
        String query = String.format("""
                    SELECT character_id as id
                    FROM characters
                    WHERE name='%s';
                """, name);
        String res = null;
        try (
            Connection conn = DriverManager.getConnection(dbUrl);
            Statement statement = conn.createStatement();
        ) {
            ResultSet set = statement.executeQuery(query);
            res = set.getString("id");
            set.close();
            conn.close();
            statement.close();
        } catch (SQLException e) {
        }
        return res;
    }

    public void clearCache() {
        String query = """
                DROP TABLE IF EXISTS characters;
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

    public boolean exists(String characterID) {
        String query = String.format("""
                    SELECT COUNT(*) as count
                    FROM characters
                    WHERE character_id='%s';
                """, characterID);
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
