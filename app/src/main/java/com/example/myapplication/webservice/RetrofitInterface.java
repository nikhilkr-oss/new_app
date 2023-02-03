package com.example.myapplication.webservice;


import com.example.myapplication.model.UpateprofileVerifyModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitInterface {


    //    @Multipart
//    @POST("restapi.php")
//    Call<OtpVerifyModel> updateUserInfo(
//            @Query("token") String token,
//            @Part(value = "phone") RequestBody phone,
//            @Part MultipartBody.Part Prof_Image,
//            @Part MultipartBody.Part Id_Proof,
//            @Part(value = "catids") RequestBody catids,
//            @Part(value = "fullname") RequestBody fullname,
//            @Part(value = "longitude") String longitude,
//            @Part(value = "latitude") String latitude
//
//
//    );
//
//    @POST("restapi.php")
//    Call<RegisterResponseModel> registerUser(@Query("token") String token, @Query("phone") String mPhone,
//
//                                             @Query("fullname") String fullname);
    @Multipart
    @POST("restapi.php")
    Call<UpateprofileVerifyModel> updateProfileInfo(
            @Query("token") String token,
            @Part(value = "phone") RequestBody phone,
            @Part MultipartBody.Part Prof_Image,
            @Part(value = "fullname") RequestBody fullname);

}
