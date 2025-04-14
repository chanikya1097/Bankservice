package com.example.bankservice.service;

import com.example.bankservice.model.Bank;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankService {

    // Retrieves nearby banks using the Google Places API "Nearby Search"
    public List<Bank> getNearbyBanks(double lat, double lng, String apiKey) {
        List<Bank> banks = new ArrayList<>();
        // 10 miles is approximately 16,093 meters
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + lat + "," + lng + "&radius=16093&type=bank&key=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        JSONObject jsonObject = new JSONObject(result);
        JSONArray results = jsonObject.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject bankJson = results.getJSONObject(i);
            String name = bankJson.getString("name");
            // "vicinity" typically contains the address information
            String address = bankJson.optString("vicinity", "No address provided");
            JSONObject location = bankJson.getJSONObject("geometry").getJSONObject("location");
            double bankLat = location.getDouble("lat");
            double bankLng = location.getDouble("lng");

            Bank bank = new Bank(name, address, bankLat, bankLng);
            banks.add(bank);
        }
        return banks;
    }
}
