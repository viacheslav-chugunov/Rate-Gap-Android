package zenith.apps.pick_currency.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.model.Currency
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.core.R
import zenith.apps.core.ui.component.CurrencyIcon

@Composable
internal fun CurrencyTile(
    currency: Currency,
    onClick: () -> Unit,
    updateFavouriteState: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(end = 24.dp)
            .shadow(
                elevation = 8.dp,
                shape = MaterialTheme.shapes.large.copy(
                    bottomStart = ZeroCornerSize,
                    topStart = ZeroCornerSize
                )
            )
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .clip(
                shape = MaterialTheme.shapes.large.copy(
                    bottomStart = ZeroCornerSize,
                    topStart = ZeroCornerSize
                )
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .then(modifier)
    ) {
        CurrencyIcon(
            currency = currency,
            modifier = Modifier.padding(end = 24.dp)
        )
        Text(
            text = currency.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = updateFavouriteState
        ) {
            Icon(
                painter = painterResource(
                    if (currency.isInFavourite) R.drawable.ic_star_shine else R.drawable.ic_star
                ),
                contentDescription = null,
                tint = if (currency.isInFavourite) MaterialTheme.colorScheme.inversePrimary else
                    MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    RateGapTheme {
        CurrencyTile(
            currency = Currency.UAH_TEST,
            onClick = {},
            updateFavouriteState = {}
        )
    }
}