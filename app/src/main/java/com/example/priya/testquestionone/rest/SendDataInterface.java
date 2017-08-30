package com.example.priya.testquestionone.rest;

import com.example.priya.testquestionone.pojo.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by priya on 25/8/17.
 */

public interface SendDataInterface {
    @FormUrlEncoded
    @POST("social_connect/")
     Call<Response> insertUser(
            @Field("social_id") String social_id,
            @Field("social_type") String username,
            @Field("devicetoken") String devicetoken,
            @Field("devicetype") String devicetype,
            @Field("email") String email,
            @Field("lat") String lat,
            @Field("lang") String lang,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname
            );
}
