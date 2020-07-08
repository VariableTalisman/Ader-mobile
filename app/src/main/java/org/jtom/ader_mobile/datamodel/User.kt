package org.jtom.ader_mobile.datamodel

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("first_name")
    var firstName: String,
    
    @SerializedName("last_name")
    var lastName: String,

    @SerializedName("brand_name")
    var brandName: String,

    @SerializedName("brand_website")
    var brandWebsite: String,
    
    @SerializedName("email")
    var email: String
)
