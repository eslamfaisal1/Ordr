package com.android.order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import com.android.order.ui.MainActivity
import com.android.order.utils.LanguageSharedHelper
import java.util.*

private const val TAG = "Activityextention"

fun Activity.changeLanguage(context: Context) {
    when (LanguageSharedHelper.getLanguage(this, Constants.LANGUAGE)) {
        getString(R.string.ar) -> {
            LanguageSharedHelper.putLanguage(this, Constants.LANGUAGE, getString(R.string.en))

        }

        getString(R.string.en) -> {
            LanguageSharedHelper.putLanguage(this, Constants.LANGUAGE, getString(R.string.ar))
        }
    }

    Log.d(TAG, "changeLanguage: "+ LanguageSharedHelper.getLanguage(this, Constants.LANGUAGE))
    setLocale(this, LanguageSharedHelper.getLanguage(this, Constants.LANGUAGE))
    navToMain(context)
}

/**
 * TODO navigate to main ad release splash component
 *
 */
fun Activity.navToMain(contect: Context) {
    val intent = Intent(contect, MainActivity::class.java)
    startActivity(intent)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    finish()
}

fun setLocale( context: Context,localeName: String) {
    val locale = Locale(localeName)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    context.resources.updateConfiguration(
        config,
        context.resources.displayMetrics
    )
}