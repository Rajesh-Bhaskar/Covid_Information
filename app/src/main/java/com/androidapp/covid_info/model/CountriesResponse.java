package com.androidapp.covid_info.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesResponse {
    @SerializedName("Countries")
    @Expose
    private List<Country> countries;
    @SerializedName("Global")
    @Expose
    private Global global;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }


}
