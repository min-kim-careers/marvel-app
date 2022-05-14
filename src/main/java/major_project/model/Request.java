package major_project.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Request {
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
            e.printStackTrace();
        }
        return null;
    }
}
