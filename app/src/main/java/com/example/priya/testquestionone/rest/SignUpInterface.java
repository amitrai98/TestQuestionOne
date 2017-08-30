package com.example.priya.testquestionone.rest;

import com.example.priya.testquestionone.pojo.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by priya on 25/8/17.
 */

public interface SignUpInterface {
    @FormUrlEncoded
    @POST("signup/")
    Call<Response> insertUser(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("email") String email,
            @Field("devicetype") String devicetype,
            @Field("devicetoken") String device_token,
            @Field("lat") String lat,
            @Field("lang") String lang,
            @Field("mobileno") String mobileno,
            @Field("usertype") String usertype,
            @Field("social_id") String social_id,
            @Field("social_type") String social_type,
            @Field("image_url") String image_url

            );
}
