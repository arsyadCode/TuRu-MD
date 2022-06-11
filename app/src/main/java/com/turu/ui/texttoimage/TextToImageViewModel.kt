package com.turu.ui.texttoimage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.turu.data.ApiConfig
import com.turu.data.history.response.CreateHistoryResponse
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.model.history.CreateHistoryRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TextToImageViewModel(private val pref: UserPreference): ViewModel()  {

    private var _listPictures = MutableLiveData<List<String>>()
    val listPictures: LiveData<List<String>> = _listPictures

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun createHistory(token: String, req: CreateHistoryRequest){
        _isLoading.postValue(true)
        val client = ApiConfig().getHistoryApi().createHistory(token,req)
        client.enqueue(object: Callback<CreateHistoryResponse>{
            override fun onResponse(
                call: Call<CreateHistoryResponse>,
                response: Response<CreateHistoryResponse>
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        val pictures= responseBody.data?.images
                        _listPictures.postValue(pictures as List<String>?)
                        Log.d(TAG, response.message().toString())
                    }
                }
                _isLoading.postValue(true)
                Log.e(TAG, response.message().toString())
            }

            override fun onFailure(call: Call<CreateHistoryResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                _isLoading.postValue(true)
            }

        })
    }

    companion object {
        const val TAG = "TextToImageViewModel"
    }
}