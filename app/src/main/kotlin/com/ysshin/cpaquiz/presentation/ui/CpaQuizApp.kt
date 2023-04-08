package com.ysshin.cpaquiz.presentation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.network.NetworkConnectivityStatusBox
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.framework.permission.PostNotification
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.presentation.MainViewModel
import com.ysshin.cpaquiz.presentation.PostNotificationUiState
import com.ysshin.cpaquiz.presentation.navigation.CpaQuizNavHost
import com.ysshin.cpaquiz.presentation.navigation.TopLevelDestination
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CpaQuizApp(appState: CpaQuizAppState) {
    CpaQuizTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        RequestPostNotificationsPermission(snackbarHostState)

        Scaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    CpaQuizBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigate,
                        currentDestination = appState.currentDestination
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            Column {
                NetworkConnectivityStatusBox(isOffline = isOffline)

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    if (appState.shouldShowNavRail) {
                        CpaQuizNavigationRail(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigate,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.safeDrawingPadding(),
                        )
                    }

                    CpaQuizNavHost(
                        navController = appState.navController,
                        windowSizeClass = appState.windowSizeClass,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding),
                        startDestination = appState.startDestination,
                    )
                }
            }
        }
    }
}

@Composable
private fun RequestPostNotificationsPermission(
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showPostNotificationsDialog by rememberSaveable { mutableStateOf(false) }
    val postNotification = viewModel.postNotification.collectAsStateWithLifecycle()
    Timber.d("post notification: ${postNotification.value}")

    SideEffect {
        val notification = postNotification.value
        if (notification is PostNotificationUiState.Success) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val isPermissionGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

                if (isPermissionGranted && notification.data == PostNotification.DENIED) {
                    Timber.d("User switched permission denied to granted directly.")
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            context.getString(R.string.post_notifications_granted),
                            withDismissAction = true
                        )
                    }
                    viewModel.grantPostNotification()
                } else if (isPermissionGranted.not() && notification.data == PostNotification.GRANTED) {
                    Timber.d("User switched permission granted to denied directly.")
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            message = context.getString(R.string.post_notifications_denied),
                            actionLabel = context.getString(R.string.settings)
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed) {
                            context.startActivity(
                                Intent().apply {
                                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                }
                            )
                        }
                    }
                    viewModel.denyPostNotification()
                }

                showPostNotificationsDialog = notification.data == PostNotification.NOT_REQUESTED
            } else {
                // POST_NOTIFICATIONS permission is automatically granted
                // in Android versions lower than API level 33 (Android 12).
                // Therefore, there is no need to request user permission
                // for this particular permission in older Android versions.
                if (notification.data != PostNotification.GRANTED) {
                    viewModel.grantPostNotification()
                }
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                viewModel.grantPostNotification()
            } else {
                viewModel.denyPostNotification()
            }
        }
    )

    if (showPostNotificationsDialog) {
        AppInfoDialog(
            icon = painterResource(id = R.drawable.ic_notifications),
            title = stringResource(id = R.string.post_notifications_dialog_title),
            description = stringResource(id = R.string.post_notifications_dialog_description),
            confirmText = stringResource(id = R.string.allow),
            dismissText = stringResource(id = R.string.close),
            onConfirm = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                showPostNotificationsDialog = false
            },
            onDismiss = {
                viewModel.denyPostNotification()
                showPostNotificationsDialog = false
            },
        )
    }
}

@Composable
private fun CpaQuizBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: Consumer<TopLevelDestination>,
    currentDestination: NavDestination?,
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        val icon = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        Icon(imageVector = icon, contentDescription = null)
                    },
                    label = {
                        Text(
                            modifier = Modifier.resourceTestTag(stringResource(id = destination.iconTextResourceId)),
                            text = stringResource(id = destination.iconTextResourceId)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CpaQuizNavigationRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: Consumer<TopLevelDestination>,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = {
                    Text(
                        modifier = Modifier.resourceTestTag(stringResource(id = destination.iconTextResourceId)),
                        text = stringResource(id = destination.iconTextResourceId)
                    )
                }
            )
        }
    }
}
