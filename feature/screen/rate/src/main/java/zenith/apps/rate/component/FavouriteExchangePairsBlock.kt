package zenith.apps.rate.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.currency.model.ExchangePair
import zenith.apps.core.R

@Composable
internal fun FavouriteExchangePairsBlock(
    exchangePairs: List<ExchangePair>,
    pickExchangePair: (ExchangePair) -> Unit,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(
        visible = exchangePairs.isNotEmpty(),
        enter = fadeIn() + slideInHorizontally(
            initialOffsetX = { -it }
        ),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX = { -it }
        )
    ) {
        Column(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.large.copy(
                        topStart = ZeroCornerSize,
                        bottomStart = ZeroCornerSize
                    )
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.favourites),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            exchangePairs.forEach { exchangePair ->
                FavouriteExchangePairTile(
                    exchangePair = exchangePair,
                    onClick = { pickExchangePair(exchangePair) }
                )
            }
        }
    }

}

@Composable
@Preview
private fun Preview() {
    RateGapTheme {
        FavouriteExchangePairsBlock(
            exchangePairs = listOf(ExchangePair.UAH_BTC_TEST),
            pickExchangePair = {}
        )
    }
}