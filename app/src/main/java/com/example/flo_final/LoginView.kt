package com.example.flo_final

interface LoginView {
    fun onLoginSuccess(code : Int, result: Result)
    fun onLoginFailure()
}