package com.ysshin.cpaquiz.feature.settings.presentation.ui

import android.content.Intent
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
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
        WindowWidthSizeClass.Expanded -> 2
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
                settingsIcon = painterResource(id = R.drawable.ic_delete),
                settingsText = stringResource(id = R.string.delete_wrong_note),
                onClick = {
                    setDeleteWrongProblemDialogOpened(true)
                },
            )
        }

        item {
            SettingsListItem(
                settingsIcon = painterResource(id = R.drawable.ic_info),
                settingsText = stringResource(id = R.string.app_version),
                onClick = {
                    setAppVersionDialogOpened(true)
                }
            )
        }

        item {
            SettingsListItem(
                settingsIcon = painterResource(id = R.drawable.ic_note_outlined),
                settingsText = stringResource(id = R.string.open_source_license),
                onClick = {
                    context.startActivity(
                        Intent(context, OssLicensesMenuActivity::class.java)
                    )
                }
            )
        }

        item {
            SettingsListItem(
                settingsIcon = painterResource(id = R.drawable.ic_mail),
                settingsText = stringResource(id = R.string.mail_to_developer),
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            context.getString(R.string.mail_to_developer_title)
                        )
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf(context.getString(R.string.developer_email))
                        )
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
                        actionLabel = context.getString(R.string.confirm)
                    )
                }
                setDeleteWrongProblemDialogOpened(false)
            },
            onDismiss = {
                setDeleteWrongProblemDialogOpened(false)
            },
            icon = painterResource(id = R.drawable.ic_delete),
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
private fun SettingsListItem(
    settingsIcon: Painter,
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
            Image(
                painter = settingsIcon,
                contentDescription = settingsText,
                modifier = Modifier.size(size = 36.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
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
        settingsIcon = painterResource(id = R.drawable.ic_note_outlined),
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
