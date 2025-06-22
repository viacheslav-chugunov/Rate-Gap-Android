package zenith.apps.rate.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zenith.apps.core.model.Currency
import zenith.apps.core.ui.theme.RateGapTheme

@Composable
internal fun CurrencyInputField(
    currency: Currency,
    value: String,
    onValueChange: (String) -> Unit,
    pickCurrency: () -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large.copy(
                    topEnd = ZeroCornerSize,
                    bottomEnd = ZeroCornerSize
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Column(
           modifier = Modifier
               .padding(end = 12.dp)
               .clip(MaterialTheme.shapes.extraSmall)
               .clickable(onClick = pickCurrency),
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
               color = MaterialTheme.colorScheme.onPrimaryContainer
           )
       }
       TextField(
           value = value,
           onValueChange = { newValue ->
               if (Regex("^$|^(0|[1-9]\\d*)(\\.\\d*)?$").matches(newValue)) {
                   onValueChange(newValue)
               }
           },
           textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
           colors = TextFieldDefaults.colors(
               unfocusedContainerColor = MaterialTheme.colorScheme.surface,
               unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
               unfocusedIndicatorColor = Color.Transparent,
               focusedContainerColor = MaterialTheme.colorScheme.surface,
               focusedTextColor = MaterialTheme.colorScheme.onSurface,
               focusedIndicatorColor = Color.Transparent
               ),
           shape = MaterialTheme.shapes.small,
           placeholder = {
               Text(
                   text = value.ifEmpty { "0" },
                   color = MaterialTheme.colorScheme.onSurface.copy(
                       alpha = if (value.isEmpty()) 0.4f else 1f
                   ),
                   textAlign = TextAlign.End,
                   modifier = Modifier.fillMaxWidth()
               )
           },
           keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Decimal,
               imeAction = imeAction
           ),
           modifier = Modifier.fillMaxWidth()
       )
    }
}

@Preview
@Composable
private fun Preview() {
    RateGapTheme {
        CurrencyInputField(
            currency = Currency.UAH_TEST,
            value = "100",
            onValueChange = {},
            pickCurrency = {}
        )
    }
}