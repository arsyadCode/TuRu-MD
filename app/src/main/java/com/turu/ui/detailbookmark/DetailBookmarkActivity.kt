package com.turu.ui.detailbookmark

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turu.databinding.ActivityDetailBookmarkBinding
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.model.bookmark.CreateBookmarkRequest
import com.turu.ui.ViewModelFactory
import com.turu.ui.bookmark.BookmarkActivity
import com.turu.ui.detaihistory.DetailHistoryActivity
import com.turu.ui.detaihistory.DetailHistoryViewModel
import com.turu.ui.history.HistoryActivity
import com.turu.ui.texttoimage.TextToImageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailBookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBookmarkBinding
    private lateinit var detailBookmarkViewModel: DetailBookmarkViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(DetailHistoryViewModel.EXTRA_ID, 0)
        val text = intent.getStringExtra(DetailHistoryViewModel.EXTRA_TEXT)
        val listPictures = intent.getStringArrayExtra(DetailHistoryViewModel.EXTRA_LIST_PICTURES)

        detailBookmarkViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailBookmarkViewModel::class.java]

        detailBookmarkViewModel.getUser().observe(this) { user ->
            this.user = user
        }

        binding.tvDetailBookmark.text = text

        binding.rvDetailBookmark.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TextToImageAdapter()
        binding.rvDetailBookmark.adapter = adapter
        if (listPictures != null) {
            adapter.setListPictures(listPictures.toList())
        }
        binding.tbBookmark.isChecked = false
        var _isChecked: MutableLiveData<Boolean> = binding.tbBookmark.isChecked as MutableLiveData<Boolean>
        val isChecked: LiveData<Boolean> = _isChecked

        isChecked.observe(this) {
            var check = it
            binding.tbBookmark.setOnClickListener{
                val token = "Bearer ${user.token}"
                if(check){
                    var createBookmarkRequest = CreateBookmarkRequest()
                    createBookmarkRequest.text = text
                    detailBookmarkViewModel.createBookmark(token, createBookmarkRequest)
                    _isChecked.postValue(false)
                } else {
                    detailBookmarkViewModel.deleteBookmark(token, id)
                    Log.d(DetailHistoryActivity.TAG, "delete bookmark")
                    _isChecked.postValue(true)
                }
            }
        }


        binding.tbBookmark.setOnClickListener {
            val token = "Bearer ${user.token}"
            detailBookmarkViewModel.deleteBookmark(token, id)
            Log.d(DetailHistoryActivity.TAG, "delete history")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this, BookmarkActivity::class.java)
        startActivity(intent)
        Log.d(TAG, "onDestroy called")
    }

    companion object {
        const val TAG = "DetailHistoryActivity"
    }
}