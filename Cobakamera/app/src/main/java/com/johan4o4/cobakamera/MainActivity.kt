package com.johan4o4.cobakamera

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val localModel = LocalModel.Builder()
            .setAssetFilePath("model.tflite")
            .build()


        val img: ImageView = findViewById(R.id.imageToLabel)
        // assets folder image file name with extension
        val fileName = "flower2.jpg"
        // get bitmap from assets folder
        val bitmap: Bitmap? = assetsToBitmap(fileName)
        bitmap?.apply {
            img.setImageBitmap(this)
        }
        val txtOutput : TextView = findViewById(R.id.txtOutput)
        val btn: Button = findViewById(R.id.btnTest)
        btn.setOnClickListener {
            val options = CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.7f)
                .setMaxResultCount(5)
                .build()
            Log.d(TAG, options.toString())

            val labeler = ImageLabeling.getClient(options)
            Log.d(TAG, labeler.toString())

            val image = InputImage.fromBitmap(bitmap!!, 0)
            Log.d(TAG, image.toString())

            var outputText = ""
            labeler.process(image)
                .addOnSuccessListener { labels ->
                    Log.d(TAG, "label.txt work ?")
                    // Task completed successfully
                    for (label in labels) {
                        Log.d(TAG, "for work ?")
                        val text = label.text
                        val confidence = label.confidence
                        outputText += "$text : $confidence\n"
                        //val index = label.txt.index
                    }
                    txtOutput.text = outputText
                    Log.d(TAG, outputText)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }

        }
        val btnGoTo: Button = findViewById(R.id.btn_go_to)
        btnGoTo.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "ASW_Main"
    }
}

// extension function to get bitmap from assets
fun Context.assetsToBitmap(fileName: String): Bitmap?{
    return try {
        with(assets.open(fileName)){
            BitmapFactory.decodeStream(this)
        }
    } catch (e: IOException) { null }
}


