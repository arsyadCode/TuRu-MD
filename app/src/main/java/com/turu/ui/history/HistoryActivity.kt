package com.turu.ui.history

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turu.databinding.ActivityHistoryBinding
import com.turu.model.UserPreference
import com.turu.ui.LoadingStateAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel
    private val adapter = HistoryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(this,
            HistoryViewModelFactory(this, UserPreference.getInstance(dataStore))
        )[HistoryViewModel::class.java]

        getData()
    }

    private fun getData() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)

        binding.rvHistory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        historyViewModel.histories.observe(this) {
            Log.d("HistoryFLow", "histories observe")
            Log.d("HistoryFLow", "${historyViewModel.histories}")
            adapter.submitData(lifecycle, it)
            Log.d(TAG,it.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    companion object {
        const val TAG = "HistoryActivity"
    }
}