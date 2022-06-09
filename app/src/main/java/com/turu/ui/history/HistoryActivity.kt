package com.turu.ui.history

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turu.R
import com.turu.data.history.GetHistoryUserIdResponseItem
import com.turu.databinding.ActivityHistoryBinding
import com.turu.model.UserPreference
import com.turu.ui.LoadingStateAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(this,
            HistoryViewModelFactory(this, UserPreference.getInstance(dataStore))
        )[HistoryViewModel::class.java]

        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        getData()
    }

    private fun getData() {
        val adapter = HistoryListAdapter()

        binding.rvHistory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        historyViewModel.histories.observe(this) {
            adapter.submitData(lifecycle, it)
            Log.d(TAG, "histories observe success")
            Log.d(TAG,it.toString())
        }

        adapter.setOnItemClickCallback(object : HistoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GetHistoryUserIdResponseItem) {

                Log.d(TAG, "${data.id}")
            }
        })
    }

    companion object {
        const val TAG = "HistoryActivity"
    }
}