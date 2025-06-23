package zenith.apps.pick_currency.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import zenith.apps.core.model.Currency
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.pick_currency.component.CurrencyTile
import zenith.apps.pick_currency.component.SearchTopBar

@Composable
fun PickCurrencyScreen(
    currencyCode: String,
    navigateBack: () -> Unit
) {
    val viewModel = hiltViewModel { factory: PickCurrencyViewModel.Factory ->
        factory.create(currencyCode)
    }

    Content(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        navigateBack = navigateBack,
        pickCurrency = viewModel::updateCurrency,
        updateSearchQuery = viewModel::updateSearchQuery,
        updateCurrencyFilter = viewModel::updateCurrencyFilter,
        updateCryptoFilter = viewModel::updateCryptoFilter,
        updateFavouriteFilter = viewModel::updateFavouriteFilter,
        updateFavouriteState = viewModel::updateFavouriteState
    )
}

@Composable
private fun Content(
    state: PickCurrencyState,
    pickCurrency: (Currency) -> Unit,
    navigateBack: () -> Unit,
    updateSearchQuery: (String) -> Unit,
    updateCurrencyFilter: (Boolean) -> Unit,
    updateCryptoFilter: (Boolean) -> Unit,
    updateFavouriteFilter: (Boolean) -> Unit,
    updateFavouriteState: (Currency) -> Unit
) {
    LaunchedEffect(state.currencyPicked) {
        if (state.currencyPicked) {
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                query = state.searchQuery,
                onQueryChange = updateSearchQuery,
                navigateBack = navigateBack,
                enableCurrenciesFilter = state.enableCurrenciesFilter,
                enableCryptoFilter = state.enableCryptoFilter,
                enableFavouriteFilter = state.enableFavouriteFilter,
                onEnableCurrenciesFilterChange = updateCurrencyFilter,
                onEnableCryptoFilterChange = updateCryptoFilter,
                onEnableFavouriteFilterChange = updateFavouriteFilter
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = state.currencies,
                key = { it.code }
            ) { currency ->
                CurrencyTile(
                    currency = currency,
                    onClick = {
                        pickCurrency(currency)
                    },
                    updateFavouriteState = {
                        updateFavouriteState(currency)
                    },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    RateGapTheme {
        Content(
            state = PickCurrencyState(
                currencies = listOf(Currency.UAH_TEST, Currency.BTC_TEST)
            ),
            navigateBack = {},
            pickCurrency = {},
            updateSearchQuery = {},
            updateCurrencyFilter = {},
            updateCryptoFilter = {},
            updateFavouriteFilter = {},
            updateFavouriteState = {}
        )
    }
}