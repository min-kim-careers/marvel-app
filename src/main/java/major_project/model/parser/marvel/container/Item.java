package major_project.model.parser.marvel.container;

public class Item {
    private String resourceURI;
    private String name;

    public String getID() {
        String[] res = resourceURI.split("/");
        return res[res.length - 1];
    }

    public String getType() {
        String[] res = resourceURI.split("/");
        return res[res.length - 2];
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}