package com.android.order.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.order.R
import com.android.order.changeLanguage
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                delay(1000)
                changeLanguage(this@SplashActivity)
            }
        }

    }


}