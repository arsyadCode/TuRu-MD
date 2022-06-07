package com.turu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.turu.R
import com.turu.databinding.ActivityMainBinding
import com.turu.model.UserPreference
import com.turu.ui.bookmark.BookmarkActivity
import com.turu.ui.history.HistoryActivity
import com.turu.ui.login.LoginActivity
import com.turu.ui.texttoimage.TextToImage

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(UserPreference.getInstance(dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getUser().observe(this) {
            if(!it.isLogin){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnTextToImage.setOnClickListener {
            startActivity(Intent(this, TextToImage::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_bookmark -> {
                val i = Intent(this, BookmarkActivity::class.java)
                startActivity(i)
            }
            R.id.action_history -> {
                val i = Intent(this, HistoryActivity::class.java)
                startActivity(i)
            }
            R.id.action_settings -> {
//                val i = Intent(this, )
//                startActivity(i)
            }
            R.id.action_logout -> {
                mainViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "Main Activity"
    }
}