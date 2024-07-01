package com.example.rescueagency;

import com.example.rescueagency.agency.NewRequestData;
import com.example.rescueagency.apiresponse.GetAgencies;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.UpdateProfile;
import com.example.rescueagency.apiresponse.map.GoogleMapResponse;
import com.example.rescueagency.apiresponse.map.ditance.DitanceAndDurationRoot;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Call<SignUpResponse> login(@Query("username")String username, @Query("password")String password);

    @GET("/category/getcategory")
    Call<GetCategoryResponse> getCategory();

    //added
    @Multipart
    @POST("/category/add_category")
    Call<SignUpResponse> addCategory(@Part("name") RequestBody categoryname,@Part MultipartBody.Part pdf);



    @POST("/use/update_profile")
    Call<SignUpResponse> updateProfile(@Body UpdateProfile updateProfile);

    @GET("/use/change_password")
    Call<SignUpResponse> changePassword(@Query("old_password")String old_password,
                                        @Query("new_password")String new_password);


    @Multipart
    @POST("/use/rescue_agency_register")
    Call<SignUpResponse> agencyRegister(@Part("agency_name") RequestBody agency_name,
                                        @Part("type_of_service")RequestBody type_of_service,
                                        @Part("address")RequestBody address,
                                        @Part("mobile")RequestBody mobile,
                                        @Part("total_members")RequestBody total_members,
                                        @Part MultipartBody.Part pdf,
                                        @Part("email")RequestBody email,
                                        @Part("username")RequestBody username,
                                        @Part("password")RequestBody password,
                                        @Part("user_type")RequestBody user_type,@Part("latitude")RequestBody latitude,
                                        @Part("longitude")RequestBody longitude,@Part("category_id")int categoryId);
    @GET("/use/get_agency")
    Call<GetAgencies> getAgencies(@Query("category_id")int agentID);
    @Multipart
    @POST("use/new_request")
    Call<SignUpResponse> newRequest(NewRequestData data, List<MultipartBody.Part> images);

    //map
    @GET("distancematrix/json")
    Call<GoogleMapResponse> getDistanceInfo(@QueryMap Map<String, String> parameters);
//    Call<GoogleMapResponse> getDistanceInfo1(@QueryMap Map<String, String> parameters);
}
