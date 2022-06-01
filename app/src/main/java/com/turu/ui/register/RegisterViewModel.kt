package com.turu.ui.register

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.turu.data.ApiConfig
import com.turu.data.user.RegisterResponse
import com.turu.model.UserPreference
import com.turu.model.user.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel() : ViewModel() {

    fun userRegister(req: RegisterRequest){
        val client = ApiConfig().getUserApi().userRegister(req)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse success: ${response.message()}")
                } else {
                    Log.e(TAG, "onResponse failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        const val TAG = "RegisterViewModel"
    }
}