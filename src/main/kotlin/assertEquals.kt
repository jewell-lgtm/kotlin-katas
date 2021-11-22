
fun <T> assertEquals(a: T?, b: T?): String =
    if (a == b) "Ok"
    else "NO! $b"
