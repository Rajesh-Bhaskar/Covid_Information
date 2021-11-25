package com.androidapp.covid_info.rest;

import com.androidapp.covid_info.model.CountriesResponse;
import com.androidapp.covid_info.model.Country;
import com.androidapp.covid_info.model.DetailsResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {


    @GET("/summary")
    Observable<CountriesResponse> getHomeScreenDetails();


    @GET("/dayone/country/{country}/status/confirmed")
    Observable<List<Country>> getDetails(@Path("country") String country);
}
