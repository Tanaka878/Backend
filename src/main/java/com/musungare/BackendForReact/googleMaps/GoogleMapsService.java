package com.musungare.BackendForReact.googleMaps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";
    private static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=5000&type=atm&key=%s";

    private final RestTemplate restTemplate = new RestTemplate();

    public String getATMLocations(double latitude, double longitude) {
        String url = String.format(PLACES_URL, latitude + "," + longitude, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    public String getGeocode(String address) {
        String url = String.format(GEOCODING_URL, address, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

    // Add this method in GoogleMapsService.java
    private static final String DISTANCE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s";

    public String getDistance(String origin, String destination) {
        String url = String.format(DISTANCE_MATRIX_URL, origin, destination, apiKey);
        return restTemplate.getForObject(url, String.class);
    }

}
