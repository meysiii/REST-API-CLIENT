import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherClient {

    public static void main(String[] args) {

        String city = "Coimbatore";
        String apiKey = "6218eea22ebea3393139b0a7ace4d612";

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + apiKey + "&units=metric";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            String cityName = json.get("name").getAsString();
            double temp = json.getAsJsonObject("main").get("temp").getAsDouble();
            int humidity = json.getAsJsonObject("main").get("humidity").getAsInt();
            String weather = json.getAsJsonArray("weather")
                    .get(0).getAsJsonObject()
                    .get("description").getAsString();

            System.out.println("------ Weather Report ------");
            System.out.println("City       : " + cityName);
            System.out.println("Temperature: " + temp + " °C");
            System.out.println("Humidity   : " + humidity + "%");
            System.out.println("Condition  : " + weather);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
