package design.after.image.Utility.ADT

/**
 * Generic ordered pair of two values.
 *
 * Similar to Kotlin's built-in Pair but with:
 * - Better naming (`first`, `second` remain but `left`/`right` aliases too)
 * - Destructuring
 * - Copy with modifications
 * - Map functions
 */
data class Pair<A, B>(
    val first: A,
    val second: B
) {
    /** Alias for first element */
    val left: A get() = first

    /** Alias for second element */
    val right: B get() = second

    /** Creates a copy with a different first value */
    fun withFirst(newFirst: A) = Pair(newFirst, second)

    /** Creates a copy with a different second value */
    fun withSecond(newSecond: B) = Pair(first, newSecond)

    /** Maps the first value */
    fun <A2> mapFirst(transform: (A) -> A2) = Pair(transform(first), second)

    /** Maps the second value */
    fun <B2> mapSecond(transform: (B) -> B2) = Pair(first, transform(second))

    /** Maps both values */
    fun <A2, B2> map(transformFirst: (A) -> A2, transformSecond: (B) -> B2) =
        Pair(transformFirst(first), transformSecond(second))
}
