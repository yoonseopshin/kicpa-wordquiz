package com.ysshin.cpaquiz.feature.settings.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ysshin.cpaquiz.core.android.BuildConfig
import com.ysshin.cpaquiz.core.android.ui.dialog.AppDialogType
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.feature.settings.R
import com.ysshin.cpaquiz.feature.settings.presentation.screen.main.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsRoute(windowSizeClass: WindowSizeClass, viewModel: SettingsViewModel = hiltViewModel()) {
    SettingsScreen(
        windowSizeClass = windowSizeClass,
        deleteAllWrongProblems = viewModel::deleteAllWrongProblems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    windowSizeClass: WindowSizeClass,
    deleteAllWrongProblems: Action,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        flingAnimationSpec = decayAnimationSpec,
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { SettingsTopAppBar() }
    ) { padding ->
        SettingsLazyVerticalGrid(
            modifier = Modifier.padding(padding),
            windowSizeClass = windowSizeClass,
            snackbarHostState = snackbarHostState,
            deleteAllWrongProblems = deleteAllWrongProblems
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.settings),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    )
}

@Composable
private fun SettingsLazyVerticalGrid(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    snackbarHostState: SnackbarHostState,
    deleteAllWrongProblems: Action,
) {
    val context = LocalContext.current

    val numberOfColumns = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> 2
        else -> 1
    }

    val (isDeleteWrongProblemDialogOpened, setDeleteWrongProblemDialogOpened) = rememberSaveable {
        mutableStateOf(false)
    }
    val (isAppVersionDialogOpened, setAppVersionDialogOpened) = rememberSaveable { mutableStateOf(false) }

    InitSettingsDialog(
        isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened,
        setDeleteWrongProblemDialogOpened = setDeleteWrongProblemDialogOpened,
        deleteAllWrongProblems = deleteAllWrongProblems,
        snackbarHostState = snackbarHostState,
        isAppVersionDialogOpened = isAppVersionDialogOpened,
        setAppVersionDialogOpened = setAppVersionDialogOpened,
    )

    LazyVerticalGrid(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 20.dp),
        columns = GridCells.Fixed(numberOfColumns)
    ) {
        item {
            SettingsListItem(
                settingsIcon = CpaIcons.Delete,
                settingsText = stringResource(id = R.string.delete_wrong_note),
                onClick = {
                    setDeleteWrongProblemDialogOpened(true)
                },
            )
        }

        item {
            SettingsListItem(
                settingsIcon = CpaIcons.Info,
                settingsText = stringResource(id = R.string.app_version),
                onClick = {
                    setAppVersionDialogOpened(true)
                }
            )
        }

        item {
            SettingsListItem(
                settingsIcon = CpaIcons.NoteOutlined,
                settingsText = stringResource(id = R.string.open_source_license),
                onClick = context::startOssLicenseActivity
            )
        }

        item {
            SettingsListItem(
                settingsIcon = CpaIcons.Mail,
                settingsText = stringResource(id = R.string.mail_to_developer),
                onClick = context::startCpaQuizContactActivity
            )
        }
    }
}

@Composable
private fun InitSettingsDialog(
    isDeleteWrongProblemDialogOpened: Boolean,
    setDeleteWrongProblemDialogOpened: Consumer<Boolean>,
    deleteAllWrongProblems: Action,
    snackbarHostState: SnackbarHostState,
    isAppVersionDialogOpened: Boolean,
    setAppVersionDialogOpened: Consumer<Boolean>,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (isDeleteWrongProblemDialogOpened) {
        AppInfoDialog(
            onConfirm = {
                deleteAllWrongProblems()
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.success_delete_all_wrong_note),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
                setDeleteWrongProblemDialogOpened(false)
            },
            onDismiss = {
                setDeleteWrongProblemDialogOpened(false)
            },
            icon = CpaIcons.Delete,
            title = stringResource(id = R.string.delete_wrong_note),
            description = stringResource(id = R.string.question_delete_all_wrong_note),
        )
    }

    if (isAppVersionDialogOpened) {
        AppInfoDialog(
            onConfirm = {
                setAppVersionDialogOpened(false)
            },
            onDismiss = {
                setAppVersionDialogOpened(false)
            },
            icon = CpaIcons.Info,
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
private fun SettingsListItem(
    settingsIcon: CpaIcon,
    settingsText: String,
    onClick: Action = {},
) {
    val cornerShape = RoundedCornerShape(24.dp)

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .bounceClickable(
                dampingRatio = 0.95f,
                onClick = onClick,
                shape = cornerShape,
            )
            .fillMaxWidth(),
        shape = cornerShape,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CpaIcon(
                icon = settingsIcon,
                contentDescription = settingsText,
                modifier = Modifier.size(size = 36.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontSize = 18.sp)) {
                        append(settingsText)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemPreview() {
    SettingsListItem(
        settingsIcon = CpaIcons.NoteOutlined,
        settingsText = stringResource(id = R.string.open_source_license),
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    CpaQuizTheme {
        BoxWithConstraints {
            SettingsScreen(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight)),
                deleteAllWrongProblems = {},
            )
        }
    }
}

private fun Context.startOssLicenseActivity() {
    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
}

/**
 * Contact via KakaoTalk if KakaoTalk have been installed, if not contact via email.
 */
private fun Context.startCpaQuizContactActivity() {
    val kakaoTalkPackageName = "com.kakao.talk"
    val kakaoTalkChannelScheme = "kakaoplus"
    val cpaQuizEncodedProfileId = "_niPxcxj"
    val uri = buildString {
        append("intent://plusfriend/talk/chat/$cpaQuizEncodedProfileId#Intent;")
        append("scheme=$kakaoTalkChannelScheme;")
        append("package=$kakaoTalkPackageName;end")
    }
    val kakaoTalkChatIntent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME)
    val isKakaoTalkInstalled = kakaoTalkChatIntent.`package`?.let { pkg ->
        packageManager.getLaunchIntentForPackage(pkg) != null
    } ?: false

    if (isKakaoTalkInstalled) {
        startActivity(kakaoTalkChatIntent)
    } else {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.mail_to_developer_title)
            )
            putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(getString(R.string.developer_email))
            )
            putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.mail_info, BuildConfig.APP_VERSION_NAME)
            )
            type = "message/rfc822"
        }
        startActivity(Intent.createChooser(emailIntent, "Email:"))
    }
}
