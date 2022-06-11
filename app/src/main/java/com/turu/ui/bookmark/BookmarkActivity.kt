package com.turu.ui.bookmark

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turu.databinding.ActivityBookmarkBinding
import com.turu.model.UserPreference
import com.turu.ui.LoadingStateAdapter
import com.turu.ui.history.HistoryActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookmarkViewModel = ViewModelProvider(this,
            BookmarkViewModelFactory(this, UserPreference.getInstance(dataStore))
        )[BookmarkViewModel::class.java]

        getData()
    }

    private fun getData() {
        binding.rvBookmark.layoutManager = LinearLayoutManager(this)

        val adapter = BookmarkListAdapter()

        binding.rvBookmark.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        bookmarkViewModel.bookmarks.observe(this) {
            Log.d("BookmarkFLow", "histories observe")
            Log.d("BookmarkFLow", "${bookmarkViewModel.bookmarks}")
            adapter.submitData(lifecycle, it)
            Log.d(HistoryActivity.TAG,it.toString())
        }
    }
}