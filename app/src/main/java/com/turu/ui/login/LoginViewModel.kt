package com.turu.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.turu.data.ApiConfig
import com.turu.data.user.LoginResponse
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.model.user.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {

    fun getUser(): LiveData<UserModel>{
        return pref.getUser().asLiveData()
    }

    fun login() {
        viewModelScope.launch {
            pref.login()
        }
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun userLogin(req: LoginRequest){
        val client = ApiConfig().getUserApi().userLogin(req)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    val id = responseBody?.data?.id.toString()
                    val name = responseBody?.data?.name.toString()
                    val email = responseBody?.data?.email.toString()
                    val photo = responseBody?.data?.photo.toString()
                    val token = responseBody?.data?.token.toString()

                    saveUser(UserModel(
                        id,
                        name,
                        email,
                        photo,
                        token,
                        true)
                    )

                    login()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}