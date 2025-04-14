package com.example.bankservice.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    // Use the same API key for all Google Maps Platform APIs (Geocoding and Places)
    private final String apiKey = "AIzaSyCRxDDOKVN3GPB3wFTgI1AZsPNXrFWUKac";  // Replace with your actual API key

    // Converts the provided zipcode into latitude and longitude using the Geocoding API
    public double[] getCoordinates(String zipcode) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = new JSONObject(result);
        JSONArray results = jsonObject.getJSONArray("results");
        if (results.length() > 0) {
            JSONObject location = results.getJSONObject(0)
                                         .getJSONObject("geometry")
                                         .getJSONObject("location");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");
            return new double[] { lat, lng };
        }
        // In a production application, consider throwing an exception or handling the error case
        return new double[] { 0.0, 0.0 };
    }

    public String getApiKey() {
        return apiKey;
    }
}
