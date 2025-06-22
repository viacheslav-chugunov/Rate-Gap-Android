package zenith.apps.rate.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.model.Currency
import zenith.apps.currency.model.ExchangePair
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.core.R
import zenith.apps.core.extension.asPrice
import zenith.apps.user_preference.model.ExchangeRateAssessment

@Composable
fun RatesBlock(
    exchangePair: ExchangePair,
    fromCurrencyInput: String,
    toCurrencyInput: String,
    exchangeRateAssessment: ExchangeRateAssessment,
    modifier: Modifier = Modifier
) {
    val fromCurrencyCode = exchangePair.fromCurrency.code.uppercase()
    val toCurrencyCode = exchangePair.toCurrency.code.uppercase()
    val fromCurrencyInputValue = remember(fromCurrencyInput) {
        fromCurrencyInput.toDoubleOrNull()
    }
    val toCurrencyInputValue = remember(toCurrencyInput) {
        toCurrencyInput.toDoubleOrNull()
    }
    val toCurrencyRealValue = remember(fromCurrencyInputValue) {
        fromCurrencyInputValue?.let { it * exchangePair.rate }
    }
    val commissionValue = remember(toCurrencyInputValue, toCurrencyRealValue) {
        if (toCurrencyInputValue != null && toCurrencyRealValue != null) {
            val difference = toCurrencyRealValue - toCurrencyInputValue
            difference
        } else {
            null
        }
    }
    val commissionPercent = remember(commissionValue, toCurrencyRealValue) {
        if (toCurrencyRealValue != null && commissionValue != null && toCurrencyRealValue != 0.0) {
            commissionValue / toCurrencyRealValue * 100
        } else {
            null
        }
    }
    val message = when {
        fromCurrencyInput.isEmpty() -> stringResource(R.string.enter_first_currency_value)
        toCurrencyInput.isEmpty() -> stringResource(R.string.enter_second_currency_value)
        commissionValue == null ||
        toCurrencyRealValue == null -> stringResource(R.string.internal_error_double_check_your_input_and_try_again)
        else -> null
    }
    val hint = when {
        commissionPercent == null -> null
        commissionPercent <= 0 ->
            stringResource(R.string.no_commission)
        commissionPercent <= exchangeRateAssessment.minimum ->
            stringResource(R.string.low_commission_up_to_format, exchangeRateAssessment.minimum.asPrice)
        commissionPercent > exchangeRateAssessment.minimum && commissionPercent <= exchangeRateAssessment.medium ->
            stringResource(R.string.medium_commission_up_to_format, exchangeRateAssessment.medium.asPrice)
        commissionPercent > exchangeRateAssessment.medium && commissionPercent <= exchangeRateAssessment.maximum ->
            stringResource(R.string.high_commission_up_to_format, exchangeRateAssessment.maximum.asPrice)
        commissionPercent > exchangeRateAssessment.maximum ->
            stringResource(R.string.very_high_commission_above_format, exchangeRateAssessment.maximum.asPrice)
        else -> null
    }

    Box {
        AnimatedVisibility(
            visible = message != null,
            enter = fadeIn() + slideInHorizontally(
                initialOffsetX = { -it }
            ),
            exit = fadeOut() + slideOutHorizontally(
                targetOffsetX = { it }
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = message ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        AnimatedVisibility(
            visible = message == null,
            enter = fadeIn() + slideInHorizontally(
                initialOffsetX = { -it }
            ),
            exit = fadeOut() + slideOutHorizontally(
                targetOffsetX = { it }
            )
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.current_rate),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = "${fromCurrencyInput.asPrice} $fromCurrencyCode = ${toCurrencyInput.asPrice} $toCurrencyCode",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = stringResource(R.string.real_rate),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "${fromCurrencyInput.asPrice} $fromCurrencyCode = ${toCurrencyRealValue?.asPrice} $toCurrencyCode",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = stringResource(R.string.commission),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "${commissionValue?.asPrice} $toCurrencyCode (${commissionPercent?.asPrice}%)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
                if (hint != null) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCalculation() {
    RateGapTheme {
        RatesBlock(
            exchangePair = ExchangePair(
                fromCurrency = Currency.UAH_TEST,
                toCurrency = Currency.BTC_TEST
            ),
            fromCurrencyInput = "100",
            toCurrencyInput = "76",
            exchangeRateAssessment = ExchangeRateAssessment.DEFAULT
        )
    }
}