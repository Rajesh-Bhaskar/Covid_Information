package com.androidapp.covid_info.model;

import androidx.versionedparcelable.ParcelField;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class  Country {

    @SerializedName("Country")
    @Expose
    private String Country;

    @SerializedName("CountryCode")
    @Expose
    private String CountryCode;

    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Cases")
    @Expose
    private String Cases;
    @SerializedName("Date")
    @Expose
    private String Date;
    @SerializedName("NewConfirmed")
    @Expose
    private String NewConfirmed;
    @SerializedName("TotalConfirmed")
    @Expose
    private String TotalConfirmed;

    @SerializedName("NewDeaths")
    @Expose
    private String NewDeaths;

    @SerializedName("TotalDeaths")
    @Expose
    private String TotalDeaths;

    @SerializedName("NewRecovered")
    @Expose
    private String NewRecovered;
    @SerializedName("TotalRecovered")
    @Expose
    private String TotalRecovered;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        NewRecovered = newRecovered;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCases() {
        return Cases;
    }

    public void setCases(String cases) {
        Cases = cases;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
