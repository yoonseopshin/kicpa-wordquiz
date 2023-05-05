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
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.ysshin.cpaquiz.designsystem.R

object CpaIcons {
    val Settings = CpaIcon.ImageVectorIcon(Icons.Outlined.Settings)
    val SettingsFilled = CpaIcon.ImageVectorIcon(Icons.Filled.Settings)
    val Edit = CpaIcon.ImageVectorIcon(Icons.Outlined.Edit)
    val EditFilled = CpaIcon.ImageVectorIcon(Icons.Filled.Edit)
    val Home = CpaIcon.ImageVectorIcon(Icons.Outlined.Home)
    val HomeFilled = CpaIcon.ImageVectorIcon(Icons.Filled.Home)
    val Play = CpaIcon.DrawableResourceIcon(R.drawable.ic_play)
    val Timer = CpaIcon.DrawableResourceIcon(R.drawable.ic_timer)
    val NoteOutlined = CpaIcon.DrawableResourceIcon(R.drawable.ic_note_outlined)
    val Delete = CpaIcon.DrawableResourceIcon(R.drawable.ic_delete)
    val Info = CpaIcon.DrawableResourceIcon(R.drawable.ic_info)
    val Mail = CpaIcon.DrawableResourceIcon(R.drawable.ic_mail)
    val Quiz = CpaIcon.DrawableResourceIcon(R.drawable.ic_quiz)
    val KeyboardArrowUp = CpaIcon.ImageVectorIcon(Icons.Filled.KeyboardArrowUp)
    val SearchOff = CpaIcon.DrawableResourceIcon(R.drawable.ic_search_off)
    val Filter = CpaIcon.DrawableResourceIcon(R.drawable.ic_filter)
    val ArrowBack = CpaIcon.ImageVectorIcon(Icons.Default.ArrowBack)
    val Check = CpaIcon.ImageVectorIcon(Icons.Default.Check)
    val Notifications = CpaIcon.DrawableResourceIcon(R.drawable.ic_notifications)
    val Warning = CpaIcon.ImageVectorIcon(Icons.Rounded.Warning)
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
