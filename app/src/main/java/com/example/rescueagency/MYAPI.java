package com.example.rescueagency;

import com.example.rescueagency.apiresponse.GetAgencies;
import com.example.rescueagency.apiresponse.GetCategoryResponse;
import com.example.rescueagency.apiresponse.SignUpResponse;
import com.example.rescueagency.apiresponse.UpdateProfile;
import com.example.rescueagency.apiresponse.agencyinfo.AgencyInfoRoot;
import com.example.rescueagency.apiresponse.getnewemergencyrequestinfo.GetNewEmergencyRequestRootClass;
import com.example.rescueagency.apiresponse.map.GoogleMapResponse;
import com.example.rescueagency.apiresponse.map.getcurrentlocation.CurrentLocationRootClass;
import com.example.rescueagency.apiresponse.map.mydistance.GetDistanceRootResponse;
import com.example.rescueagency.apiresponse.newrequest.NewRequestRootClass;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("/use/get_team")
    Call<AgencyInfoRoot> getTeam(@Query("agent_id")String id);

    @Multipart
    @POST("/use/add_member/add_member")
    Call<SignUpResponse> addMember(@Part("agent_id") RequestBody agent_id,@Part("name") RequestBody name,@Part("mobile") RequestBody mobile,
                                   @Part("email") RequestBody email,@Part("address") RequestBody address,
                                   @Part("dob") RequestBody dob,
                                   @Part("role") RequestBody role,@Part("year_of_experience") RequestBody year_of_experience,
                                   @Part MultipartBody.Part profilePhoto);
    @DELETE("/use/add_member/delete_member")
    Call<SignUpResponse> deleteMember(@Query("id")String id);
    //added
    @Multipart
    @POST("/category/add_category")
    Call<SignUpResponse> addCategory(@Part("name") RequestBody categoryname,@Part MultipartBody.Part pdf);

    @POST("/use/update_profile")
    Call<SignUpResponse> updateProfile(@Body UpdateProfile updateProfile);
//
//    @Multipart
//    @POST("/use/change_profile_image")
//    Call<SignUpResponse> updateProfileImage( @Part("user_id") RequestBody userId, @Part MultipartBody.Part image);

    @GET("/use/change_password")
    Call<SignUpResponse> changePassword(@Query("old_password")String old_password,
                                        @Query("password")String new_password,@Query("user_id")String id);

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
    Call<SignUpResponse> newRequest(@Part("agent_id")RequestBody agencyId,@Part("agency_name")RequestBody agentName,
                                    @Part("user_id")RequestBody userId,@Part("user_name")RequestBody userName,@Part("mobile")RequestBody mobile,
                                    @Part("describe_incident")RequestBody describeIncident,@Part("category_id")RequestBody categoryId,
                                    @Part("type_of_incident")RequestBody typeOfIncident,@Part("status")RequestBody status,@Part("latitude")RequestBody latitude,
                                    @Part("longitude")RequestBody longitude, @Part List<MultipartBody.Part> images);

    @GET("/use/get_request_info")
    Call<GetNewEmergencyRequestRootClass> getRequestInfo(@Query("request_id")String requestId);
    @GET("/use/get_new_emergency_request")
    Call<NewRequestRootClass> getRequest(@Query("agency_id")String agentId);
    /* Google Map API */
    // get distance and duration
    @GET("distancematrix/json")
    Call<GoogleMapResponse> getDistanceInfo(@QueryMap Map<String, String> parameters);
    @GET("distancematrix/json")
    Call<GetDistanceRootResponse> getDistanceInfoMyWay(@QueryMap Map<String, String> parameters);
    //get current location address
    @GET("geocode/json")
    Call<CurrentLocationRootClass> getCurrentLocation(@Query("address")String address,@Query("key")String key);
//    Call<GoogleMapResponse> getDistanceInfo1(@QueryMap Map<String, String> parameters);
}
