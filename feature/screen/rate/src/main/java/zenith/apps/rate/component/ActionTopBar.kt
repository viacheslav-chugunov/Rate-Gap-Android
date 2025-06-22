package zenith.apps.rate.component

import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import zenith.apps.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActionTopBar(
    swapExchangePair: () -> Unit
) {
    TopAppBar(
        title = {},
        actions = {
            IconButton(
                onClick = swapExchangePair
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_swap),
                    contentDescription = null
                )
            }
        }
    )
}