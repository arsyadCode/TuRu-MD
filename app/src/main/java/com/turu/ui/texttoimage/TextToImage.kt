package com.turu.ui.texttoimage

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.turu.databinding.ActivityTextToImageBinding
import com.turu.model.UserModel
import com.turu.model.UserPreference
import com.turu.model.history.CreateHistoryRequest
import com.turu.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TextToImage : AppCompatActivity() {

    private lateinit var binding: ActivityTextToImageBinding
    private lateinit var textToImageViewModel: TextToImageViewModel
    private lateinit var user: UserModel

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextToImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textToImageViewModel = ViewModelProvider(this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[TextToImageViewModel::class.java]

        textToImageViewModel.getUser().observe(this) { user ->
            this.user = user
        }

        binding.rvTranslateTextToImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = TextToImageAdapter()
        binding.rvTranslateTextToImage.adapter = adapter
        textToImageViewModel.listPictures.observe(this) {
            adapter.setListPictures(it)
        }

        binding.translateImageButton.setOnClickListener{
            var createHistoryRequest = CreateHistoryRequest()
            createHistoryRequest.text = binding.translateEditText.text.toString()
            textToImageViewModel.createHistory("Bearer ${user.token}",createHistoryRequest)
            Log.d(TAG, "ListPictures, ${textToImageViewModel.listPictures}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    companion object {
        const val TAG = "TextToImage"
    }
}