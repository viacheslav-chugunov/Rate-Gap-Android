package zenith.apps.core.model

class Currency(
    val code: String,
    val rate: Double
) {

    override fun equals(other: Any?): Boolean =
        other is Currency && other.code == code

    override fun hashCode(): Int = code.hashCode()
}