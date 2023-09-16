package com.example.myapplication

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.InputStream
import java.security.Permissions
import java.util.UUID

class ProFile:AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ProfileBinding
    lateinit var storage: FirebaseStorage
    lateinit var db: FirebaseFirestore
    private var REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.CAMERA
    )
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK && it.data != null) {
            val uri = it.data!!.data
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .into(binding.circlePic)
                uploadImageToFirebase(uri)
            } else {
                Toast.makeText(this, "uri없음", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun uploadImageToFirebase(imageUri: Uri) {
        val docRef=db.collection("users").document("${auth.currentUser!!.uid}")
        docRef.get().addOnSuccessListener {
                DocumentSnapshot->
            if(DocumentSnapshot.exists()){
                val temp=DocumentSnapshot.toObject(User::class.java)
                if(temp!=null&&temp.filename!=""){
                    val storageRef: StorageReference = storage.reference
                    val imgRef: StorageReference = storageRef.child("images/${temp.filename}")
                    imgRef.delete()
                        .addOnSuccessListener {
                            Toast.makeText(binding.root.context,"기존 이미지 삭제를 성공했습니다",Toast.LENGTH_LONG).show()
                            db.collection("users").document("${auth.currentUser!!.uid}")
                                .update("filename","")
                            db.collection("users").document("${auth.currentUser!!.uid}")
                                .update("profileurl","")

                        }
                        .addOnFailureListener {
                            Log.d("setOn","img삭제 실패")
                        }

                }
            }
        }
        val storageRef: StorageReference = storage.reference
        val email = auth.currentUser?.email ?: "default"
        val fileName = "${email.substringBefore('@')}_${UUID.randomUUID()}.jpg"
        val imgRef: StorageReference = storageRef.child("images/$fileName")
        // ContentResolver를 사용하여 이미지 데이터를 업로드합니다.
        val inputStream: InputStream? = applicationContext.contentResolver.openInputStream(imageUri)
        if (inputStream != null) {
            val uploadTask = imgRef.putStream(inputStream)

            uploadTask.addOnSuccessListener {
                imgRef.downloadUrl.addOnSuccessListener { uri ->
                    val user = User(
                        "${auth.currentUser!!.uid}",
                        "${auth.currentUser!!.email}",
                        uri.toString(),
                       fileName,
                        ""
                    )

                    db.collection("users")
                        .document("${auth.currentUser!!.uid}")
                        .set(user)
                }.addOnFailureListener {
                    Log.d("setOn","이미지url을 가져오는 데에 실패했습니다.")
                }
            }.addOnFailureListener {
                Toast.makeText(this, "업로드에 실패하였습니다", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d("setOn", "InputStream이 비어 있습니다.")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 33) {
            REQUIRED_PERMISSIONS += android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            REQUIRED_PERMISSIONS += android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            REQUIRED_PERMISSIONS += android.Manifest.permission.READ_EXTERNAL_STORAGE
        }
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        Permissions()
        binding.uploadGallery.isInvisible = true
    }
    inner class Permissions {
        private fun showPermissionRationaleDialog() {
            val alertDialogBuilder = AlertDialog.Builder(binding.root.context)
            alertDialogBuilder.setTitle("권한 필요")
            alertDialogBuilder.setMessage("앱을 사용하려면 권한이 필요합니다.권한 설정 거부시 앱 사용이 어려울 수 있습니다.")
            alertDialogBuilder.setPositiveButton("설정으로 이동") { _, _ ->
                // 설정 액티비티를 열어서 권한 설정 화면으로 이동
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton("취소") { dialog, _ ->
                // 권한 요청이 거부된 경우 처리할 내용 추가
                dialog.dismiss()
            }
            alertDialogBuilder.setCancelable(false)
            alertDialogBuilder.show()
        }

        public fun hasPermissions(): Boolean {
            for (i in REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(
                        binding.root.context,
                        i
                    ) != PackageManager.PERMISSION_GRANTED
                ) {showPermissionRationaleDialog()
                    return false
                }
            }
            onGalleryLoad()
            return true
        }
    }


    override fun onStart() {
        super.onStart()
        val doc=db.collection("users").document("${auth.currentUser!!.uid}")
        doc.get().addOnSuccessListener {
            DocumentSnapshot->
            if(DocumentSnapshot.exists()){
                val obj=DocumentSnapshot.toObject(User::class.java)
                if(obj!!.nickname==""){
                    doc.update("nickname","${auth.currentUser!!.email}")
                    obj!!.nickname=="${auth.currentUser!!.email}"
                    binding.nickname.text=obj.nickname
                }
            }
        }
        binding.circlePic.setOnClickListener {
            Log.d("setOn", "click event success")
            binding.uploadGallery.isInvisible = false
            binding.uploadGallery.setOnClickListener {
                AlertDialog.Builder(this)
                    .setTitle("프로필 사진 변경 안내")
                    .setMessage("기존 프로필 사진이 삭제되고 새로운 프로필을 업로드합니다")
                    .setNeutralButton("동의합니다",object :DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            onGalleryLoad()
                        }
                    })
                    .create()
                    .show()
            }
        }
    }

    private fun onGalleryLoad() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activityResult.launch(intent)
    }

}