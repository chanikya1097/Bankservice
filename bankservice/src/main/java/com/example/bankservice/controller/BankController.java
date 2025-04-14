package com.example.bankservice.controller;

import com.example.bankservice.model.Bank;
import com.example.bankservice.service.BankService;
import com.example.bankservice.service.GoogleMapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {

    private final BankService bankService;
    private final GoogleMapsService googleMapsService;

    @Autowired
    public BankController(BankService bankService, GoogleMapsService googleMapsService) {
        this.bankService = bankService;
        this.googleMapsService = googleMapsService;
    }

    // Endpoint: GET /banks?zipcode=XXXXX
    @GetMapping
    public List<Bank> getNearbyBanks(@RequestParam String zipcode) {
        // 1. Convert the zipcode to latitude and longitude
        double[] coordinates = googleMapsService.getCoordinates(zipcode);
        double userLat = coordinates[0];
        double userLng = coordinates[1];

        // 2. Use the Places API (via BankService) to get real-time bank locations within 10 miles
        List<Bank> nearbyBanks = bankService.getNearbyBanks(userLat, userLng, googleMapsService.getApiKey());
        return nearbyBanks;
    }
}
