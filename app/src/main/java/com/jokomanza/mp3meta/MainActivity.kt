package com.jokomanza.mp3meta

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.developer.filepicker.model.DialogConfigs
import com.developer.filepicker.model.DialogProperties
import com.developer.filepicker.view.FilePickerDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import ealvatag.audio.AudioFileIO
import ealvatag.tag.FieldKey
import kotlinx.coroutines.InternalCoroutinesApi
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var dialog: FilePickerDialog

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = MainActivityViewModel()

        Dexter.withContext(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    println("Permission Granted")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    println("Permission Denied")
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    println("onPermissionRationaleShouldBeShown")
                }

            })
            .check()

        val properties = DialogProperties()
        properties.selection_mode = DialogConfigs.SINGLE_MODE
        properties.selection_type = DialogConfigs.FILE_SELECT
        properties.root = File(DialogConfigs.DEFAULT_DIR)
        properties.error_dir = File(DialogConfigs.DEFAULT_DIR)
        properties.offset = File(DialogConfigs.DEFAULT_DIR)
        properties.extensions = null
        properties.show_hidden_files = false

        dialog = FilePickerDialog(this@MainActivity, properties)
        dialog.setTitle("Select a File")
        dialog.setDialogSelectionListener {
            //files is the array of the paths of files selected by the Application User.
            it.forEach {
                Log.d("Result", "onCreate: $it")
                val file = File(it)
                val audioFile = AudioFileIO.read(file)
//                audioFile.tag.get().deleteField(FieldKey.LYRICS)
//                audioFile.save()

                val lyrics = audioFile.tag.get().getFields(FieldKey.LYRICS)
                println("Old Lyrics : $lyrics")
                viewModel.search(file.nameWithoutExtension).observe(this, {
                    Log.d("Result", "First Response : $it")
                    viewModel.getSong(it.response.hits[0].result.id.toString())
                        .observe(this, { song ->
                            Log.d("Result", "Second Response : $song")

                            Log.d("Result", "URL : ${song.response.song.url}")

                            val url = song.response.song.url.substring(19)
                            viewModel.getPage(url).observe(this, { result ->
                                Log.d("Result", "Final Result : ${result}")
                                audioFile.tag.get().setField(FieldKey.LYRICS, result)
                                audioFile.save()
                                println(
                                    "New Lyrics : " + audioFile.tag.get().getFields(FieldKey.LYRICS)
                                )
                                findViewById<TextView>(R.id.hello_world).text = result
                            })
                        })
                })
            }
        }

        findViewById<TextView>(R.id.hello_world).setOnClickListener {
            dialog.show()
        }

//        openSomeActivityForResult()
    }


    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                Log.d("Result", "Data : ${result.data?.dataString}")
            }
        }

    private fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
            Intent.createChooser(this, "Choose a file")
        }
        resultLauncher.launch(intent)
    }

    private fun println(value: Any?) {
        Log.d("Result", "$value")
    }

    //Add this method to show Dialog when the required permission has been granted to the app.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (dialog != null) {   //Show dialog if the read permission has been granted.
                        dialog.show()
                    }
                } else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(
                        this@MainActivity,
                        "Permission is Required for getting list of files",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}