package com.tanzible.restapilogindeno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {

    @POST(AppConstant.ENDPOINT.LOGIN)
    Call<LoginResponse> login_task(@Body Phone phone);

    @POST(AppConstant.ENDPOINT.CONFRIM_OTP)
    Call<UserDataResponse>confirm_otp(@Body VerifyModel verifyModel);


}
