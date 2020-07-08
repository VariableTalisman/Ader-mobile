package org.jtom.ader_mobile.ui.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.Response
import org.jtom.ader_mobile.common.AderViewModel
import org.jtom.ader_mobile.common.Constants
import org.jtom.ader_mobile.common.GrantType
import org.jtom.ader_mobile.request.login.LoginResponse
import org.jtom.ader_mobile.service.api.ApiClient
import org.jtom.ader_mobile.ui.login.model.LoginModel
import org.jtom.ader_mobile.ui.login.model.LoginViewModelAction
import org.jtom.ader_mobile.util.SessionManager
import java.net.HttpURLConnection

class LoginViewModel(private val context: Context) : ViewModel(),
    AderViewModel<LoginModel, LoginViewModelAction> {

    override val model = MutableLiveData<LoginModel>()
    private val _text = MutableLiveData<String>().apply {
        value = "Login"
    }
    val text: LiveData<String> = _text

    override fun send(action: LoginViewModelAction) {
        when (action) {
            is LoginViewModelAction.Login -> performLogin(action.email, action.password)
        }
    }

    private fun performLogin(email: String, password: String) = viewModelScope.launch {
        val sessionManager = SessionManager.getInstance(context)
        val apiClient = ApiClient(context)

        apiClient.getApiService().login(
            Credentials.basic(Constants.CLIENT_ID, Constants.CLIENT_SECRET),
            email,
            password,
            GrantType.PASSWORD.value).clone()
            .enqueue(object : retrofit2.Callback<LoginResponse> {

                override fun onFailure(loginResponse: retrofit2.Call<LoginResponse>, t: Throwable) {
                    model.postValue(LoginModel.Error(t.message!!))
                }

                override fun onResponse(
                    call: retrofit2.Call<LoginResponse>,
                    loginResponse: retrofit2.Response<LoginResponse>
                ) {
                    if (loginResponse.isSuccessful && loginResponse.code() == HttpURLConnection.HTTP_OK) {
                        sessionManager.saveAuthToken(loginResponse.body()!!.accessToken)
                        model.postValue(LoginModel.Success)
                    } else {
                        model.postValue(LoginModel.Error(loginResponse.code().toString().plus(": ").plus(loginResponse.message())))
                    }
                }
            })
    }
}
