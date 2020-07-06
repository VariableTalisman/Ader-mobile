package org.jtom.ader_mobile.request.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("access_token")
    var accessToken: String,

    @SerializedName("token_type")
    var tokenType: String,

    @SerializedName("refresh_token")
    var refreshToken: String,

    @SerializedName("expires_in")
    var expiration: String,

    @SerializedName("scope")
    var scope: String,

    @SerializedName("jti")
    var jti: String
)
