package com.turu.ui.imagetotext

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.turu.databinding.ActivityImageToTextBinding
import com.turu.ml.AslQuant
import com.turu.utils.rotateBitmap
import com.turu.utils.uriToFile
import com.turu.utils.createTempFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File


class ImageToTextActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityImageToTextBinding
    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Don't have permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageToTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bitmap: Bitmap?


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        val inputString = application.assets.open("label.txt").bufferedReader().use{it.readText()}
        var label = inputString.split("\n")

        binding.cameraXButton.setOnClickListener { startCameraX() }
//        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener {

            val model = AslQuant.newInstance(this)

            // get image
            bitmap = binding.previewImageView.drawable.toBitmap()

            // set format image
            bitmap = Bitmap.createScaledBitmap(bitmap!!,64,64,true)

            if(bitmap != null) {
                val tensorImage = TensorImage(DataType.FLOAT32)
                Log.d("shape", "byteBuffer $tensorImage")
                tensorImage.load(bitmap)
                Log.d("shape", "byteBuffer $tensorImage")

                val byteBuffer = tensorImage.buffer
                Log.d("shape", "byteBuffer $byteBuffer")

                // Creates inputs for reference.
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(byteBuffer)
                Log.d("shape", "input : ${inputFeature0.buffer}")

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                for (i in outputFeature0.floatArray) {
                    var index = 0
                    Log.d("shape", "index : $index, output : $i")
                    index++
                }

                var max = getMax(outputFeature0.floatArray, label)
                Log.d("shape", "index : $max")

                binding.textOutput.text = label[max]
                Log.d("shape", label[max])

            } else {
                binding.textOutput.text = "Can't recognize"
            }
            model.close()
        }
    }

    private fun getMax(arr:FloatArray, label:List<String>) : Int{
        var index = 0
        var min = 0.0f

        for(i in label.indices){
            if(arr[i]>min){
                index = i
                min = arr[i]
            }
        }
        return index

    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@ImageToTextActivity,
                "com.johan4o4.cobakamera",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                true
            )

            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ImageToTextActivity)
            getFile = myFile

            binding.previewImageView.setImageURI(selectedImg)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val TAG = "ASW_AddStory"
    }
}
