package zenith.apps.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.model.Currency
import zenith.apps.core.ui.theme.RateGapTheme

@Composable
fun CurrencyIcon(
    currency: Currency,
    modifier: Modifier = Modifier,
    codeColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = currency.icon),
            contentDescription = currency.name,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .size(48.dp)
        )
        Text(
            text = "[${currency.code.uppercase()}]",
            style = MaterialTheme.typography.labelMedium,
            color = codeColor
        )
    }
}

@Composable
@Preview
private fun Preview() {
    RateGapTheme {
        CurrencyIcon(
            currency = Currency.UAH_TEST
        )
    }
}