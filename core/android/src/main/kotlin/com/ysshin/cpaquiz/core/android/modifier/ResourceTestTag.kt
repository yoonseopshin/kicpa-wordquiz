package com.ysshin.cpaquiz.core.android.modifier

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.resourceTestTag(tag: String) =
    then(Modifier.semantics { testTagsAsResourceId = true })
        .then(testTag(tag))
