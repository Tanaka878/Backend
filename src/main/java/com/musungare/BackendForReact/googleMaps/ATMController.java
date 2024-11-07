package com.musungare.BackendForReact.googleMaps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atm")
public class ATMController {

    @Autowired
    private GoogleMapsService googleMapsService;

    @GetMapping("/find")
    public String findATMs(@RequestParam double latitude, @RequestParam double longitude) {
        return googleMapsService.getATMLocations(latitude, longitude);
    }

    @GetMapping("/geocode")
    public String geocode(@RequestParam String address) {
        return googleMapsService.getGeocode(address);
    }
}
