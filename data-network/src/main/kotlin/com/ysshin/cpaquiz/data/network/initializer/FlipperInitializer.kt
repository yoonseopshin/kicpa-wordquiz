package com.ysshin.cpaquiz.data.network.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.ysshin.cpaquiz.data.network.BuildConfig

object Flipper {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(FlipperInitializer::class.java)
    }
}

class FlipperInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        SoLoader.init(context, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            val plugins = listOf(
                InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()),
                NetworkFlipperPlugin(),
            )
            plugins.map { plugin -> client.addPlugin(plugin) }
            client.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
