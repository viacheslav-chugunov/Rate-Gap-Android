package zenith.apps.rate.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import zenith.apps.core.model.Currency
import zenith.apps.currency.model.ExchangePair
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.rate.component.ActionTopBar
import zenith.apps.rate.component.CurrencyInputField
import zenith.apps.rate.component.RatesBlock

@Composable
fun RateScreen(
    pickCurrency: (Currency) -> Unit
) {
    val viewModel = hiltViewModel<RateViewModel>()

    Content(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        updateFromCurrencyInput = viewModel::updateFromCurrencyInput,
        updateToCurrencyInput = viewModel::updateToCurrencyInput,
        pickCurrency = pickCurrency,
        swapExchangePair = viewModel::swapExchangePair
    )
}

@Composable
private fun Content(
    state: RateState,
    updateFromCurrencyInput: (String) -> Unit,
    updateToCurrencyInput: (String) -> Unit,
    pickCurrency: (Currency) -> Unit,
    swapExchangePair: () -> Unit
) {
    if (state.exchangePair == null) return

    Scaffold(
        topBar = {
            ActionTopBar(
                swapExchangePair = swapExchangePair
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
        ) {
            CurrencyInputField(
                currency = state.exchangePair.fromCurrency,
                value = state.fromCurrencyInput,
                onValueChange = updateFromCurrencyInput,
                pickCurrency = {
                    pickCurrency(state.exchangePair.fromCurrency)
                },
                hint = state.fromCurrencyHint,
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 12.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.large.copy(
                            topEnd = ZeroCornerSize,
                            bottomEnd = ZeroCornerSize
                        )
                    )
            )
            CurrencyInputField(
                currency = state.exchangePair.toCurrency,
                value = state.toCurrencyInput,
                onValueChange = updateToCurrencyInput,
                imeAction = ImeAction.Done,
                pickCurrency = {
                    pickCurrency(state.exchangePair.toCurrency)
                },
                hint = state.toCurrencyHint,
                modifier = Modifier
                    .padding(start = 24.dp, bottom = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.large.copy(
                            topEnd = ZeroCornerSize,
                            bottomEnd = ZeroCornerSize
                        )
                    )
            )
            RatesBlock(
                exchangePair = state.exchangePair,
                fromCurrencyInput = state.fromCurrencyInput,
                toCurrencyInput = state.toCurrencyInput,
                exchangeRateAssessment = state.exchangeRateAssessment,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.medium
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RateGapTheme {
        Content(
            state = RateState(
                exchangePair = ExchangePair(
                    fromCurrency = Currency.UAH_TEST,
                    toCurrency = Currency.BTC_TEST
                ),
                fromCurrencyInput = "100",
                toCurrencyInput = "76"
            ),
            updateFromCurrencyInput = {},
            updateToCurrencyInput = {},
            pickCurrency = {},
            swapExchangePair = {}
        )
    }
}