package com.example.rescueagency;

import com.example.rescueagency.apiresponse.GetAgencies;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.map.GoogleMapResponse;
import com.example.rescueagency.apiresponse.map.ditance.DitanceAndDurationRoot;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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

    @Multipart
    @GET("/use/agency_register")
    Call<SignUpResponse> agencyRegister(@Part("agency_name")String agency_name,
                                        @Part("type_of_service")String type_of_service,
                                        @Part("address")String address,
                                        @Part("mobile")String mobile,
                                        @Part("total_members")String total_members,
                                        @Part MultipartBody.Part pdf,
                                        @Part("email")String email,
                                        @Part("username")String username,
                                        @Part("password")String password,
                                        @Part("user_type")String user_type);
    @GET("/use/get_agency")
    Call<GetAgencies> getAgencies(@Query("category_id")int agentID);

    //map
    @GET("distancematrix/json")
    Call<GoogleMapResponse> getDistanceInfo(@QueryMap Map<String, String> parameters);
//    Call<GoogleMapResponse> getDistanceInfo1(@QueryMap Map<String, String> parameters);
}
