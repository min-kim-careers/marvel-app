package major_project.model;

import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javafx.concurrent.Task;

public class Request extends Task<JsonObject> {
    private String url;

    public Request(String url) {
        this.url = url;
    }

    public JsonObject getResponse(String url) {
        try {
            URI uri = new URI(url);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            HttpClient client = HttpClient.newBuilder().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response == null) {
                throw new IllegalArgumentException();
            }

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            return jsonObject;
        } catch (Exception e) {
            try {
                PrintWriter out = new PrintWriter("error.txt");
                out.println(e.toString());
            } catch (Exception io) {
                
            }
        }
        return null;
    }

    @Override
    protected JsonObject call() throws Exception {
        return getResponse(url);
    }
}
