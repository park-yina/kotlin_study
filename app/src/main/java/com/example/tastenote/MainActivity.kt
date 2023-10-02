package com.example.tastenote

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.PermissionChecker
import com.airbnb.lottie.BuildConfig
import com.example.tastenote.databinding.ActivitymainBinding
import com.example.tastenote.ui.theme.TasteNoteTheme
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class MainActivity() : ComponentActivity() {
    lateinit var binding: ActivitymainBinding
    private var REQUIRE_PERMISSION = arrayOf(android.Manifest.permission.INTERNET)
    val naverLogin=SocialLogin.Naver()
    private lateinit var oAuthLoginCallback:OAuthLoginCallback

    inner class Permission() {
        private fun onShowPermissionDialog() {
            AlertDialog.Builder(binding.root.context)
                .setTitle("권한 설정 안내")
                .setMessage("권한 설정을 하지 않을 시 일부 앱 사용에 지장이 있을 수 있습니다.")
                .setPositiveButton("설정으로 이동") { _, _ ->
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("scheme", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }
                .setNegativeButton("취소") { _, _ ->
                    Toast.makeText(this@MainActivity, "권한 설정 요청을 취소하셨습니다", Toast.LENGTH_LONG).show()
                }
                .setCancelable(false)
                .show()
        }

        fun permissionCheck(): Boolean {
            for (i in REQUIRE_PERMISSION) {
                if (ContextCompat.checkSelfPermission(
                        binding.root.context,
                        i
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    onShowPermissionDialog()
                    return false
                }
            }
            return true
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitymainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val permissionSetting = Permission()
        if (permissionSetting.permissionCheck()) {
            NaverIdLoginSDK.initialize(
                this,
                getString(R.string.social_login_naver_client_id),
                getString(R.string.social_login_naver_client_password),
                "테이스팅노트"
            )
        }
    }

    override fun onStart() {
        super.onStart()
        oAuthLoginCallback=naverLogin.oAuthLoginCallback
        binding.naverLogin.setOnClickListener{
            NaverIdLoginSDK.authenticate(this,oAuthLoginCallback)
        }
    }
}