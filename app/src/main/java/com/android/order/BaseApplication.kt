package com.android.order

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.android.order.ui.cart.CartViewModel
import com.android.order.ui.viewmodel.ProductsViewModel
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig

class BaseApplication : Application() {




    override fun onCreate() {
        super.onCreate()
        val config = ImagePipelineConfig.newBuilder(this)
            .setProgressiveJpegConfig(SimpleProgressiveJpegConfig())
            .setResizeAndRotateEnabledForNetwork(true)
            .setDownsampleEnabled(true)
            .build()
        Fresco.initialize(this, config)

    }

}