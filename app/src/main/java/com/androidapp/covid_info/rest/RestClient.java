package com.androidapp.covid_info.rest;

import android.content.Context;
import android.text.TextUtils;

import com.androidapp.covid_info.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private ApiService apiService;
    private Context mContext;


    public static String getBaseUrlBasedOnBuildType() {
        String base = null;

        base = BuildConfig.BASE_URL;

        return base;
    }


    public RestClient(final Context context) {
        mContext = context;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addNetworkInterceptor(loggingInterceptor).build();
        } else
            httpClient.build();


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {


                Request original = chain.request();

                HttpUrl originalHttpUrl = original.url();

                HttpUrl.Builder httpbuilderObj = originalHttpUrl.newBuilder();


                HttpUrl url = httpbuilderObj.build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(getBaseUrlBasedOnBuildType())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        apiService = restAdapter.create(ApiService.class);
    }

    public static String getBaseUrl() {
        return getBaseUrlBasedOnBuildType();
    }

    public ApiService getApiService() {
        return apiService;
    }

}

