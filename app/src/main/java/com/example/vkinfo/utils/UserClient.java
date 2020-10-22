package com.example.vkinfo.utils;

import com.example.vkinfo.models.Login;
import com.example.vkinfo.models.Seance;
import com.example.vkinfo.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("/authenticate")
    Call<User> login(@Body Login login);

    @GET("/api-cinema/list-all-seances")
    Call<ResponseBody> getAllSeances(@Header("Authorization") String authToken);

    @POST("/api-seance/add")
    Call<ResponseBody> addSeance(@Header("Authorization") String authToken, @Body Seance seance);
}
