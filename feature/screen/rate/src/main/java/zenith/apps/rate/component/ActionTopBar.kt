package zenith.apps.rate.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import zenith.apps.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ActionTopBar(
    isExchangePairInFavourite: Boolean,
    swapExchangePair: () -> Unit,
    updateFavouriteExchangePairState: () -> Unit
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
            IconButton(
                onClick = updateFavouriteExchangePairState
            ) {
                Icon(
                    painter = painterResource(
                        if (isExchangePairInFavourite) R.drawable.ic_star_shine else R.drawable.ic_star
                    ),
                    contentDescription = null,
                    tint = if (isExchangePairInFavourite) MaterialTheme.colorScheme.primary else
                        MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}