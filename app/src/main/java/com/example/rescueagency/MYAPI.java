package com.example.rescueagency;

import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MYAPI {

    @GET("/use/signup")
    Call<SignUpResponse> signUp(@Query("name")String name,@Query("phone")String phone
                                ,@Query("email")String email,
                                @Query("address")String address,@Query("dob")String dob,
                                @Query("gender")String gender,
                                @Query("username")String username,
                                @Query("password")String password,@Query("user_type")String user_type);

    @GET("/use/login")
    Call<SignUpResponse> login(@Query("username")String username,
                                @Query("password")String password);

    @GET("/category/getcategory")
    Call<GetCategoryResponse> getCategory();
    //added
}
