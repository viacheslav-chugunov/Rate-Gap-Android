package zenith.apps.core.extension

import java.math.BigDecimal
import java.util.Locale

val Double.asPrice: String
    get() {
        val plain = BigDecimal(this).stripTrailingZeros().toPlainString()
        val parts = plain.split(".")
        val integerPart = parts[0]
        val fractionalPart = parts.getOrNull(1)
        return when {
            fractionalPart == null || fractionalPart.all { it == '0' } -> integerPart
            integerPart == "0" && fractionalPart.startsWith("00") -> {
                val nonZeroIndex = fractionalPart.indexOfFirst { it != '0' }
                val significantFraction = fractionalPart.take(nonZeroIndex + 1)
                "$integerPart.$significantFraction"
            }
            fractionalPart.startsWith("00") -> integerPart
            else -> String.format(Locale.US, "%.2f", this).trimEnd('0').trimEnd('.')
        }
    }

val String.asPrice: String
    get() = toDoubleOrNull()?.asPrice ?: ""