package com.example.sectest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoCodingService {
    @GET("maps/api/geocode/json")
    Call<GeoCodeResponse> getAddressFromCoordinates(
            @Query("latlng") String latlng,
            @Query("key") String apiKey
    );
}