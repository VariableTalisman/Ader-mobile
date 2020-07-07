package org.jtom.ader_mobile.service.api

import org.jtom.ader_mobile.model.OfferDto
import org.jtom.ader_mobile.request.login.LoginResponse
import org.jtom.ader_mobile.util.Constants
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for defining REST request methods
 */
interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Header("Authorization") bearerToken: String,
        @Field("username") email: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Call<LoginResponse>

    @GET(Constants.OFFER_URL)
    fun getOffers(): Call<List<OfferDto>>
}
