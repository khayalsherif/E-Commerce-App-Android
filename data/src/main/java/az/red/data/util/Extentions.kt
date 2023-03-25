package az.red.data.util

import java.util.*

fun String.capitalizeCustom():String {
    return this.replaceFirstChar { fl ->
        if (fl.isLowerCase()) fl.titlecase(
            Locale.getDefault()
        ) else fl.toString()
    }
}