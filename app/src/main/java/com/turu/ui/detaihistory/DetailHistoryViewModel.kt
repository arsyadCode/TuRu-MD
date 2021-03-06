package com.turu.ui.detaihistory

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.turu.data.ApiConfig
import com.turu.data.history.response.DeleteResponse
import com.turu.model.UserModel
import com.turu.model.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryViewModel( private val pref: UserPreference): ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun deleteHistory(token: String, id: Int){
        _isLoading.postValue(true)
        val client = ApiConfig().getHistoryApi().delHistory(token, id)
        client.enqueue(object: Callback<DeleteResponse>{
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                if(response.isSuccessful) {
                    Log.d(TAG, response.message())
                } else {
                    Log.e(TAG, response.message())
                }
                _isLoading.postValue(false)
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, t.message.toString())
            }

        })

    }

    companion object {
        const val TAG = "DetailHistoryViewModel"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TEXT = "extra_text"
        const val EXTRA_LIST_PICTURES = "extra_list_pictures"
    }
}