package com.cpa.cpa_word_problem.initializers

import android.content.Context
import androidx.startup.Initializer
import com.cpa.cpa_word_problem.BuildConfig
import com.cpa.cpa_word_problem.di.InitializerEntryPoint
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import javax.inject.Inject

class FlipperInitializer : Initializer<Unit> {

    @Inject
    lateinit var networkFlipperPlugin: NetworkFlipperPlugin

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context).inject(this)
        SoLoader.init(context, false)
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(context)) {
            val client = AndroidFlipperClient.getInstance(context)
            val plugins = listOf(
                InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()),
                networkFlipperPlugin,
            )
            plugins.map { plugin -> client.addPlugin(plugin) }
            client.start()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(DependencyGraphInitializer::class.java)

}