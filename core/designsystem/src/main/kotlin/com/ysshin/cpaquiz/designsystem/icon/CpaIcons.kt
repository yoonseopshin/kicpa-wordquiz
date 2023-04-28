package com.ysshin.cpaquiz.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.ysshin.cpaquiz.designsystem.R

object CpaIcons {
    // TODO: Add project icons
    val Settings = Icons.Outlined.Settings
    val SettingsFilled = Icons.Filled.Settings
    val Edit = Icons.Outlined.Edit
    val EditFilled = Icons.Filled.Edit
    val Home = Icons.Outlined.Home
    val HomeFilled = Icons.Filled.Home
    val Play = R.drawable.ic_play
    val Timer = R.drawable.ic_timer
    val NoteOutlined = R.drawable.ic_note_outlined
    val Delete = R.drawable.ic_delete
    val Info = R.drawable.ic_info
    val Mail = R.drawable.ic_mail
    val Quiz = R.drawable.ic_quiz
    val KeyboardArrowUp = Icons.Filled.KeyboardArrowUp
    val SearchOff = R.drawable.ic_search_off
    val Filter = R.drawable.ic_filter
    val ArrowBack = Icons.Default.ArrowBack
    val Check = Icons.Default.Check
}

sealed class CpaIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : CpaIcon()
    data class DrawableResourceIcon(@DrawableRes val resId: Int) : CpaIcon()
}

@Composable
fun CpaIcon(
    icon: CpaIcon,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    when (icon) {
        is CpaIcon.ImageVectorIcon -> Icon(
            imageVector = icon.imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint,
        )

        is CpaIcon.DrawableResourceIcon -> Icon(
            painter = painterResource(id = icon.resId),
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint,
        )
    }
}
