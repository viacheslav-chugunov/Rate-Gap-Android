package zenith.apps.rate.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.extension.asPrice
import zenith.apps.core.ui.component.CurrencyIcon
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.currency.model.ExchangePair

@Composable
internal fun FavouriteExchangePairTile(
    exchangePair: ExchangePair,
    onClick: () -> Unit
) {
    val rate = remember(exchangePair) {
        "1 : ${exchangePair.rate.asPrice}"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyIcon(
            currency = exchangePair.fromCurrency,
            codeColor = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = rate,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            textAlign = TextAlign.Center
        )
        CurrencyIcon(
            currency = exchangePair.toCurrency,
            codeColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
@Preview
private fun Preview() {
    RateGapTheme {
        FavouriteExchangePairTile(
            exchangePair = ExchangePair.UAH_BTC_TEST,
            onClick = {}
        )
    }
}