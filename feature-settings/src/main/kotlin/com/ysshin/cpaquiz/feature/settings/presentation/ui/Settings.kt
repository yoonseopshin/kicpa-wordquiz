package com.ysshin.cpaquiz.feature.settings.presentation.ui

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ysshin.cpaquiz.feature.settings.R
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme

@Composable
fun SettingsScreen() {
    CpaQuizTheme {
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.settings),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    },
                    elevation = 0.dp,
                    backgroundColor = colorResource(id = R.color.theme_color)
                )
            }
        ) { contentPadding ->
            SettingsContent(padding = contentPadding)
        }
    }
}

@Composable
fun SettingsContent(padding: PaddingValues) {
    Row(modifier = Modifier.padding(padding.calculateTopPadding())) {
        OpenSourceLicenseItem()
    }
}

@Composable
fun OpenSourceLicenseItem() {
    val context = LocalContext.current
    val cornerShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(
                border = BorderStroke(width = 0.dp, Color.Transparent),
                shape = cornerShape
            )
            .clip(cornerShape)
            .background(color = MaterialTheme.colors.primary.copy(alpha = 0.15f))
            .clickable(onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            })
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_note),
            contentDescription = "",
            modifier = Modifier.size(size = 36.dp),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.item_highlight_color))
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 18.sp)) {
                    append(stringResource(id = R.string.open_source_license))
                }
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun SettingsUiPreview() {
    SettingsScreen()
}
