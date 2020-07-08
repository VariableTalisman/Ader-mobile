package org.jtom.ader_mobile.ui.login.model

import org.jtom.ader_mobile.request.login.LoginResponse
import retrofit2.Response

sealed class LoginModel {
    object Loading : LoginModel()
    object Success : LoginModel()
    data class Error(val message: String) : LoginModel()
}
