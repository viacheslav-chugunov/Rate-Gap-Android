package zenith.apps.pick_currency.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    enableCurrenciesFilter: Boolean,
    enableCryptoFilter: Boolean,
    enableFavouriteFilter: Boolean,
    onEnableCurrenciesFilterChange: (Boolean) -> Unit,
    onEnableCryptoFilterChange: (Boolean) -> Unit,
    onEnableFavouriteFilterChange: (Boolean) -> Unit,
    navigateBack: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = query.ifEmpty { stringResource(R.string.enter_currency) },
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = if (query.isEmpty()) 0.4f else 1f
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            },
            actions = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = enableCurrenciesFilter,
                onClick = {
                    onEnableCurrenciesFilterChange(!enableCurrenciesFilter)
                },
                label = {
                    Text(
                        text = stringResource(R.string.currencies)
                    )
                },
            )
            FilterChip(
                selected = enableCryptoFilter,
                onClick = {
                    onEnableCryptoFilterChange(!enableCryptoFilter)
                },
                label = {
                    Text(
                        text = stringResource(R.string.crypto)
                    )
                },
            )
            FilterChip(
                selected = enableFavouriteFilter,
                onClick = {
                    onEnableFavouriteFilterChange(!enableFavouriteFilter)
                },
                label = {
                    Text(
                        text = stringResource(R.string.favourite)
                    )
                },
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    RateGapTheme {
        SearchTopBar(
            query = "Query",
            onQueryChange = {},
            navigateBack = {},
            enableCurrenciesFilter = true,
            enableCryptoFilter = true,
            enableFavouriteFilter = false,
            onEnableCurrenciesFilterChange = {},
            onEnableCryptoFilterChange = {},
            onEnableFavouriteFilterChange = {}
        )
    }
}