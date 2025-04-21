package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyscanner.SearchResult;
import com.skyscanner.api.SearchResource;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {
        // No initialization needed for now
    }

    @Override
    public void run(final HoenScannerConfiguration configuration, final Environment environment) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<SearchResult>> typeReference = new TypeReference<>() {};
        List<SearchResult> searchResults = new ArrayList<>();

        try {
            InputStream hotelsStream = getClass().getResourceAsStream("/hotels.json");
            InputStream carsStream = getClass().getResourceAsStream("/rental_cars.json");

            if (hotelsStream != null) {
                searchResults.addAll(mapper.readValue(hotelsStream, typeReference));
            }
            if (carsStream != null) {
                searchResults.addAll(mapper.readValue(carsStream, typeReference));
            }

        } catch (Exception e) {
            e.printStackTrace(); // You can replace this with logging later
        }

        // ✅ Register SearchResource to expose the /search endpoint
        SearchResource resource = new SearchResource(searchResults);
        environment.jersey().register(resource);
    }
}
