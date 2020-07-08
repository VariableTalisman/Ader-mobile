package org.jtom.ader_mobile.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.login_fragment.*
import org.jtom.ader_mobile.R
import org.jtom.ader_mobile.common.dismissKeyboard
import org.jtom.ader_mobile.ui.login.model.LoginModel
import org.jtom.ader_mobile.ui.login.model.LoginViewModelAction
import org.jtom.ader_mobile.ui.login.viewmodel.LoginViewModel
import org.jtom.ader_mobile.ui.login.viewmodel.LoginViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var unbinder: Unbinder

    @BindView(R.id.text_login) lateinit var textView: TextView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(requireContext())).get(LoginViewModel::class.java)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        loginViewModel.model.observe(viewLifecycleOwner, Observer {
            dispatchUIUpdate(it)
        })

        loginButton.setOnClickListener {
            onLoginButtonPressed()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.login_fragment, container, false)
        unbinder = ButterKnife.bind(this, root)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    private fun onLoginButtonPressed() {
        view?.dismissKeyboard()
        loginViewModel.send(LoginViewModelAction.Login(editTextEmail.text.toString(), editTextPassword.text.toString()))
    }

    private fun dispatchUIUpdate(model: LoginModel) {
        progressBar.visibility = View.GONE

        when (model) {
            is LoginModel.Loading -> {
                progressBar.visibility = View.VISIBLE
            }

            is LoginModel.Success -> {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.login_fragment_to_home_fragment)
            }

            is LoginModel.Error -> {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("Error")
                    .setMessage(model.message)
                    .setNegativeButton("Ok", null)
                    .show()
            }
        }
    }
}
