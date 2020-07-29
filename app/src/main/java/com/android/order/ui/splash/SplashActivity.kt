package com.android.order.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.order.*
import com.android.order.utils.LanguageSharedHelper
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                delay(1000)
                setLocale(this@SplashActivity, LanguageSharedHelper.getLanguage(this@SplashActivity, Constants.LANGUAGE))
                navToMain(this@SplashActivity)
            }
        }

    }


}