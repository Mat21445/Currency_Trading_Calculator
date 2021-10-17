package com.example.cinkciarz;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnectToAPI {
    String apiUrl;
    String apiString;

    public ConnectToAPI(String api_url) {
        this.apiUrl = api_url;
    }

    public void DownloadAPIstr() {
        HttpClient client = HttpClient.newHttpClient(); //client creation
        HttpRequest request = HttpRequest.newBuilder() //request creation
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(apiUrl))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            apiString = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(apiString);
        }

    }

    public String getApiString() {
        return apiString;
    }

    public void setApiUrl(String api_url) {
        this.apiUrl = api_url;
    }
}
