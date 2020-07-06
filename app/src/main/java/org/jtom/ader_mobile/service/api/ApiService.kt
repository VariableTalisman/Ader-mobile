package org.jtom.ader_mobile.service.api

import org.jtom.ader_mobile.request.login.LoginResponse
import org.jtom.ader_mobile.util.Constants
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interface for defining REST request functions
 */
interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Header("Authorization") auth: String,
        @Field("username") email: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Call<LoginResponse>
}
