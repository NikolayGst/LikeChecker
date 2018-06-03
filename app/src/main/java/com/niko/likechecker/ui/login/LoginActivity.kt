package com.niko.likechecker.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.niko.likechecker.R
import com.niko.likechecker.ui.main.MainActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (VKSdk.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            btnLogin.setOnClickListener {
                VKSdk.login(this@LoginActivity, VKScope.GROUPS, VKScope.VIDEO, VKScope.WALL, VKScope.FRIENDS)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        // Пользователь успешно авторизовался
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }

                    override fun onError(error: VKError) {
                        // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
