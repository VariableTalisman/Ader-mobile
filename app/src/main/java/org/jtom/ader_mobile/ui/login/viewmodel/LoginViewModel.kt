package org.jtom.ader_mobile.ui.login.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import org.jtom.ader_mobile.ui.login.model.LoginModel
import org.jtom.ader_mobile.ui.login.model.LoginViewModelAction
import org.jtom.ader_mobile.common.AderViewModel
import org.jtom.ader_mobile.common.Constants
import org.jtom.ader_mobile.common.GrantType
import org.jtom.ader_mobile.service.api.ApiClient
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
        val apiClient = ApiClient()

        val loginResponse = apiClient.getApiService().login(
            Credentials.basic(Constants.CLIENT_ID, Constants.CLIENT_SECRET),
            email,
            password,
            GrantType.PASSWORD.value)

        withContext(Dispatchers.Main) {
            if (loginResponse.isSuccessful && loginResponse.code() == HttpURLConnection.HTTP_OK) {
                sessionManager.saveAuthToken(loginResponse.body()!!.accessToken)
                model.postValue(LoginModel.Success)
            } else {
                val errorMessage = loginResponse.code()
                    .toString() + ": " + loginResponse.message() + ". " + loginResponse.body()
                    .toString()
                model.postValue(LoginModel.Error(errorMessage))
            }
        }
    }
}
