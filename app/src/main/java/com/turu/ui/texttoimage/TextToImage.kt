package com.turu.ui.texttoimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.turu.R
import com.turu.databinding.ActivityTextToImageBinding

class TextToImage : AppCompatActivity() {

    private lateinit var binding: ActivityTextToImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_image)
    }
}