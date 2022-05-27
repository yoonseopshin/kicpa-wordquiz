package com.ysshin.cpaquiz.feature.settings.presentation.ui

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ysshin.cpaquiz.feature.settings.R
import com.ysshin.cpaquiz.feature.settings.presentation.screen.main.SettingsViewModel
import com.ysshin.cpaquiz.shared.android.BuildConfig
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppDialogType
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.base.Action

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    CpaQuizTheme {
        val scaffoldState = rememberScaffoldState()
        val context = LocalContext.current

        LaunchedEffect(key1 = scaffoldState) {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is SettingsViewModel.UiEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                                message = event.message.asString(context),
                                actionLabel = event.actionLabel.asString(context)
                        )
                    }
                }
            }
        }

        Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    CompositionLocalProvider(LocalElevationOverlay provides null) {
                        TopAppBar(
                                title = {
                                    Text(
                                            text = stringResource(id = R.string.settings),
                                            modifier = Modifier.fillMaxWidth(),
                                    )
                                },
                                backgroundColor = colorResource(id = R.color.theme_color)
                        )
                    }
                }
        ) { contentPadding ->
            Column(
                    modifier = Modifier
                            .padding(contentPadding)
                            .padding(top = 20.dp)
            ) {
                InitSettingsDialog()

                SettingsItem(
                        settingsIcon = painterResource(id = R.drawable.ic_delete),
                        settingsText = stringResource(id = R.string.delete_wrong_note),
                        onClick = {
                            viewModel.updateDeleteWrongProblemDialogOpened(true)
                        },
                )

                SettingsItem(
                        settingsIcon = painterResource(id = R.drawable.ic_info),
                        settingsText = stringResource(id = R.string.app_version),
                        onClick = {
                            viewModel.updateAppVersionDialogOpened(true)
                        }
                )

                SettingsItem(
                        settingsIcon = painterResource(id = R.drawable.ic_note_outlined),
                        settingsText = stringResource(id = R.string.open_source_license),
                        onClick = {
                            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                        }
                )

                SettingsItem(
                        settingsIcon = painterResource(id = R.drawable.ic_mail),
                        settingsText = stringResource(id = R.string.mail_to_developer),
                        onClick = {
                            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_to_developer_title))
                                putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.developer_email)))
                                putExtra(
                                        Intent.EXTRA_TEXT,
                                        context.getString(R.string.mail_info, BuildConfig.APP_VERSION_NAME)
                                )
                                type = "message/rfc822"
                            }
                            context.startActivity(Intent.createChooser(emailIntent, "이메일:"))
                        }
                )
            }
        }
    }
}

@Composable
fun InitSettingsDialog(viewModel: SettingsViewModel = viewModel()) {
    val openDeleteWrongProblemDialog = viewModel.isDeleteWrongProblemDialogOpened.collectAsState()
    if (openDeleteWrongProblemDialog.value) {
        AppInfoDialog(
                onConfirm = {
                    viewModel.deleteAllWrongProblems()
                    viewModel.updateDeleteWrongProblemDialogOpened(false)
                },
                onDismiss = {
                    viewModel.updateDeleteWrongProblemDialogOpened(false)
                },
                icon = painterResource(id = R.drawable.ic_delete),
                title = stringResource(id = R.string.delete_wrong_note),
                description = stringResource(id = R.string.question_delete_wrong_note),
        )
    }

    val openAppVersionDialog = viewModel.isAppVersionDialogOpened.collectAsState()
    if (openAppVersionDialog.value) {
        AppInfoDialog(
                onConfirm = {
                    viewModel.updateAppVersionDialogOpened(false)
                },
                onDismiss = {
                    viewModel.updateAppVersionDialogOpened(false)
                },
                icon = painterResource(id = R.drawable.ic_info),
                title = stringResource(id = R.string.app_version),
                description = stringResource(
                        id = R.string.app_version_name_and_code,
                        BuildConfig.APP_VERSION_NAME,
                        BuildConfig.APP_VERSION_CODE
                ),
                dialogType = AppDialogType.OnlyConfirm
        )
    }
}

@Composable
fun SettingsItem(
        settingsIcon: Painter,
        settingsText: String,
        onClick: Action = {},
) {
    val cornerShape = RoundedCornerShape(24.dp)
    Row(
            modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .border(
                            border = BorderStroke(width = 0.dp, Color.Transparent),
                            shape = cornerShape
                    )
                    .clip(cornerShape)
                    .background(color = MaterialTheme.colors.primary.copy(alpha = 0.15f))
                    .clickable(onClick = onClick)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
                painter = settingsIcon,
                contentDescription = settingsText,
                modifier = Modifier.size(size = 36.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.item_highlight_color))
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp)) {
                        append(settingsText)
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
