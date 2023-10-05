package com.example.tastenote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import com.google.android.gms.auth.api.Auth

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import androidx.activity.result.registerForActivityResult
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.oauth.view.NidOAuthLoginButton.Companion.launcher
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

open class SocialLogin {
    class Naver : SocialLogin() {
        val oAuthLoginCallback: OAuthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                Log.w("test", "AccessToken : " + NaverIdLoginSDK.getAccessToken())
                val token = NaverIdLoginSDK.getAccessToken()
                Log.w("test", "client id :${(R.string.social_login_naver_client_id)}")
                Log.w("test", "ReFreshToken : " + NaverIdLoginSDK.getRefreshToken())
                Log.w("test", "Expires : " + NaverIdLoginSDK.getExpiresAt().toString())
                Log.w("test", "TokenType : " + NaverIdLoginSDK.getTokenType())
                Log.w("test", "State : " + NaverIdLoginSDK.getState().toString())
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://openapi.naver.com/v1/nid/me/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val naverRetrofit = retrofit.create(NaverRetrofit::class.java)
                val authorization = "Bearer $token"
                val call = naverRetrofit.getUserInfo(authorization)
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Log.d("naver", "통신 성공")
                            val gson = Gson()
                            val json = response.body()?.string()
                            val responseData = gson.fromJson(json, ApiResonse::class.java)
                            Log.w("naver", "${responseData.resultcode}")
                            Log.w("naver", "${responseData.response.id}")
                            Log.w("naver", "${responseData.response.nickname}")

                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.w("naver", "eroor...:${t}")
                    }
                })
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, "에러 발생")
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val code = NaverIdLoginSDK.getLastErrorCode().code
                Log.w("naver", "오류코드:${code}")
            }

        }
    }

    class Google : SocialLogin() {
        lateinit var auth: FirebaseAuth
        private lateinit var requestLauncher: ActivityResultLauncher<Intent>
        public fun setRequestLauncher(requestLauncher: ActivityResultLauncher<Intent>) {
            this.requestLauncher = requestLauncher
        }

        public fun performLauncher(data: Intent?) {
            auth = FirebaseAuth.getInstance()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task != null) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val idToken = account.idToken
                    if (idToken != null) {
                        val credential = GoogleAuthProvider.getCredential(idToken, null)
                        auth.signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("testing", "구글 로그인 성공")
                                } else {
                                    Log.d("testing", "구글 로그인 실패")
                                }
                            }
                    } else {
                        Log.d("testing", "idToken이 없습니다.")
                    }
                } catch (e: ApiException) {
                    Log.d("testing", "예외처리: ${e.statusCode}")
                }
            } else {
                Log.d("testing", "GoogleSignIn 결과가 없습니다.")
            }
        }
    }
    class Kakao:SocialLogin(){
        
    }
}