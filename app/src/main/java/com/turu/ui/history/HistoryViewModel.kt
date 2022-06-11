package com.turu.ui.history

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.turu.data.history.response.GetHistoryUserIdResponseItem
import com.turu.data.history.HistoryRepository
import com.turu.model.UserModel
import com.turu.model.UserPreference

class HistoryViewModel(historyRepository: HistoryRepository, private val pref: UserPreference): ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    val histories: LiveData<PagingData<GetHistoryUserIdResponseItem>> =
        getUser().switchMap {
            Log.d("HistoryFLow", "histories in view model")
            historyRepository.getHistories("Bearer ${it.token}",it.id).cachedIn(viewModelScope)
        }
}