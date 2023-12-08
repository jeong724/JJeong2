package com.example.flo_final

import android.telecom.Call
import android.util.Log
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signUp(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: retrofit2.Call<AuthResponse>, response: Response<AuthResponse>){
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000->signUpView.onSignUpSuccess()
                    else->signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable){
                Log.d("SIGNUP/FAILURE", t.message.toString())

            }

        })
        Log.d("SIGNUP", "Hello")
    }

    fun login(user: User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: retrofit2.Call<AuthResponse>, response: Response<AuthResponse>){
                Log.d("LOGIN/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!

                when(val code = resp.code){
                    1000->loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable){
                Log.d("SIGNUP/FAILURE", t.message.toString())

            }

        })
        Log.d("SIGNUP", "Hello")
    }
}