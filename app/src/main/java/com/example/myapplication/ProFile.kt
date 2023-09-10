package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.myapplication.databinding.ProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn.hasPermissions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import io.grpc.Context.Storage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.security.Permissions
import java.text.SimpleDateFormat
import java.util.Date

class ProFile:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ProfileBinding
    lateinit var storage: FirebaseStorage
    private var filePath: String? = null
    private val REQUEST_CAMERA_PERMISSION = 101
    private val REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()
        Permissions().check()

    }
    inner class Permissions {
       public fun check() {
            if (!hasPermissions()) {
                ActivityCompat.requestPermissions(
                    this@ProFile,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CAMERA_PERMISSION
                )
            }
        }

        public fun hasPermissions(): Boolean {
            for (i in REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(
                        this@ProFile,
                        i
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
            return true
        }
    }

    override fun onStart() {
        super.onStart()
        val storageRef: StorageReference = storage.reference
        var fileName = "IMAGE_${SimpleDateFormat("yyyymmdd_HHmmss").format(Date())}_.png"
        val imgRef: StorageReference = storage.reference.child("images/").child(fileName)
        val permissions = Permissions()
        if (permissions.hasPermissions()) {
            binding.circlePic.setOnClickListener {
                onUpload()
            }
        }
    }

    private fun onUpload() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File = createImg()

        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.myapplication.provider",
            photoFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val option = BitmapFactory.Options()
                option.inSampleSize = 10
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath, option)
                bitmap?.let {
                    binding.circlePic.setImageBitmap(bitmap)
                    onUploadFireBase(photoFile)
                }
            }
        }
        requestCameraFileLauncher.launch(intent)
    }

    private fun createImg(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        filePath = file.absolutePath
        return file
    }

    private fun onUploadFireBase(file: File) {
        val storageRef: StorageReference = storage.reference
        val email = auth.currentUser?.email ?: "default"
        val fileName = "${email.substringBefore('@')}.jpg"
        val imgRef: StorageReference = storageRef.child("images/$fileName")
        val uploadTask = imgRef.putFile(Uri.fromFile(file))
        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "업로드에 성공했습니다", Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnFailureListener {
            Toast.makeText(this, "업로드에 실패하였습니다", Toast.LENGTH_LONG).show()
        }
    }


}