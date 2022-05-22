package com.ysshin.cpaquiz.shared.android.bridge

import android.content.Context
import android.content.Intent
import com.ysshin.cpaquiz.shared.android.util.Constants

private const val MainActivityClassName = "com.cpa.cpa_word_problem.presentation.MainActivity"
private const val MainTabClassName = "com.cpa.cpa_word_problem.presentation.MainTab"

private fun getEnum(enumClassName: String, enumValue: String): Enum<*> =
    (Class.forName(enumClassName).enumConstants as Array<Enum<*>>)
        .first { it.name == enumValue }

fun homeTabIntent(context: Context, flags: Int? = null) = Intent(context, Class.forName(MainActivityClassName)).apply {
    putExtra(Constants.destination, getEnum(MainTabClassName, "Home"))
    flags?.let { addFlags(it) }
}

fun noteTabIntent(context: Context, flags: Int? = null) = Intent(context, Class.forName(MainActivityClassName)).apply {
    putExtra(Constants.destination, getEnum(MainTabClassName, "Note"))
    flags?.let { addFlags(it) }
}

fun settingsTabIntent(context: Context, flags: Int? = null) =
    Intent(context, Class.forName(MainActivityClassName)).apply {
        putExtra(Constants.destination, getEnum(MainTabClassName, "Settings"))
        flags?.let { addFlags(it) }
    }
