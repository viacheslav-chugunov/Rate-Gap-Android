package zenith.apps.splash.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import zenith.apps.core.ui.theme.RateGapTheme
import zenith.apps.core.R

@Composable
fun SplashScreen(
    hideSplash: () -> Unit,
    openRateScreen: () -> Unit
) {
    val viewModel = hiltViewModel<SplashViewModel>()

    Content(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        hideSplash = hideSplash,
        retry = viewModel::loadCurrencies,
        openRateScreen = openRateScreen
    )
}

@Composable
private fun Content(
    state: SplashState,
    hideSplash: () -> Unit,
    retry: () -> Unit,
    openRateScreen: () -> Unit = {}
) {

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            hideSplash()
        }
    }

    LaunchedEffect(state.isDataLoaded) {
        if (state.isDataLoaded) {
            openRateScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.fetching_data),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else if (state.error != null) {
            Text(
                text = stringResource(id = R.string.failed_to_fetch_data),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Button(
                onClick = retry
            ) {
                Text(
                    text = stringResource(id = R.string.retry)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview() {
    RateGapTheme(
        darkTheme = false
    ) {
        Content(
            state = SplashState(),
            hideSplash = {},
            retry = {}
        )
    }
}