package com.musungare.BackendForReact.googleMaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
@CrossOrigin("*")  // Allow all origins (use specific origins in production)
public class ATMController {

    private final GoogleMapsService googleMapsService;

    @Autowired
    public ATMController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    // Endpoint to find nearby ATMs based on latitude and longitude
    @GetMapping("/find")
    public String findATMs(@RequestParam double latitude, @RequestParam double longitude) {
        return googleMapsService.getATMLocations(latitude, longitude);
    }

    // Endpoint to geocode an address (get latitude and longitude)
    @GetMapping("/geocode")
    public String geocode(@RequestParam String address) {
        return googleMapsService.getGeocode(address);
    }
}
