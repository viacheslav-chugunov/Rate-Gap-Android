package zenith.apps.pick_currency.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.ZeroCornerSize
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

@Composable
internal fun CurrencyTile(
    currency: Currency,
    onClick: () -> Unit,
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 24.dp)
        ) {
            Image(
                painter = painterResource(currency.icon),
                contentDescription = currency.name,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .size(24.dp)
            )
            Text(
                text = "[${currency.code}]",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Text(
            text = currency.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview
@Composable
private fun Preview() {
    RateGapTheme {
        CurrencyTile(
            currency = Currency.UAH_TEST,
            onClick = {}
        )
    }
}