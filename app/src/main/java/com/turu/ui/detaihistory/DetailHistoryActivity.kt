package com.turu.ui.detaihistory

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turu.databinding.ActivityDetailHistoryBinding
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.ui.ViewModelFactory
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_ID
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_LIST_PICTURES
import com.turu.ui.detaihistory.DetailHistoryViewModel.Companion.EXTRA_TEXT
import com.turu.ui.history.HistoryActivity
import com.turu.ui.texttoimage.TextToImageAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var detailHistoryViewModel: DetailHistoryViewModel
    private lateinit var user: UserModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        val text = intent.getStringExtra(EXTRA_TEXT)
        val listPictures = intent.getStringArrayExtra(EXTRA_LIST_PICTURES)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailHistoryViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailHistoryViewModel::class.java]

        detailHistoryViewModel.getUser().observe(this) { user ->
            this.user = user
        }

        binding.tvDetailHistory.text = text

        binding.rvDetailHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TextToImageAdapter()
        binding.rvDetailHistory.adapter = adapter
        if (listPictures != null) {
            adapter.setListPictures(listPictures.toList())
        }

        binding.btnDelHistory.setOnClickListener {
            val token = "Bearer ${user.token}"
            detailHistoryViewModel.deleteHistory(token, id)
            Log.d(TAG, "delete history")
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    companion object {
        const val TAG = "DetailHistoryActivity"
    }
}