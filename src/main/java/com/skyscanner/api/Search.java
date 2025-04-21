package com.skyscanner.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Search {
    @JsonProperty
    private String city;

    public Search() {
        // Default constructor required for Jackson
    }

    public String getCity() {
        return city;
    }
}
