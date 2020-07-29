package com.android.order.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.order.R
import com.android.order.ui.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                delay(1000)

                navToMain()
            }
        }

    }

    /**
     * TODO navigate to main ad release splash component
     *
     */
    fun navToMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

}