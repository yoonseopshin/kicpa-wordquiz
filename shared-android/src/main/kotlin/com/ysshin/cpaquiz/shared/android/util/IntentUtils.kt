@file:Suppress("DEPRECATION")

package com.ysshin.cpaquiz.shared.android.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Serializable?> Bundle.serializable(key: String, clazz: Class<T>) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, clazz)
    else -> getSerializable(key) as? T
}

inline fun <reified T : Parcelable?> Bundle.parcelable(key: String, clazz: Class<T>) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, clazz)
    else -> getParcelable(key) as? T
}

inline fun <reified T : Parcelable?> Bundle.parcelableArrayList(key: String, clazz: Class<T>) = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayList(key, clazz)
    else -> getParcelableArrayList(key)
}