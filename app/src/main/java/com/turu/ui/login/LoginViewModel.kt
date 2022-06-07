package com.turu.ui.login

import android.util.Log
import androidx.lifecycle.*
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

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
        _isLoading.postValue(true)
        _isLogin.postValue(false)
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

                    Log.d(TAG, id)
                    Log.d(TAG, name)
                    Log.d(TAG, email)
                    Log.d(TAG, photo)
                    Log.d(TAG, token)

                    saveUser(UserModel(
                        id,
                        name,
                        email,
                        photo,
                        token,
                        true)
                    )
                    Log.d(TAG, response.message().toString())
                } else {
                    Log.e(TAG, response.message().toString())
                }
                _isLoading.postValue(false)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, t.message.toString())
            }

        })
    }

    companion object {
        const val TAG = "LoginViewModel"
    }
}