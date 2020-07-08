package org.jtom.ader_mobile.ui.login.model

sealed class LoginViewModelAction {
    data class Login(var email: String, var password: String) : LoginViewModelAction()
}
