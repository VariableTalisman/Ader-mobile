package org.jtom.ader_mobile.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import okhttp3.Credentials
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.request.login.LoginResponse
import org.jtom.ader_mobile.service.api.ApiClient
import org.jtom.ader_mobile.util.Constants
import org.jtom.ader_mobile.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var unbinder: Unbinder
    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private val TAG: String = "LoginFragment"

    @BindView(R.id.text_login) lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiClient = ApiClient()
        sessionManager = SessionManager(this.requireContext())

        apiClient.getApiService().login(
            Credentials.basic(Constants.CLIENT_ID, Constants.CLIENT_SECRET),
            "dev@dev.com",
            "dev",
            "password")
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, t.message, t)
                }

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        sessionManager.saveAuthToken(loginResponse.accessToken)
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                    }
                }
            })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        unbinder = ButterKnife.bind(this, root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
}
