package com.moisesborges.tvaddict.utils

import android.os.Build
import android.text.Html

/**
 * Created by moises.anjos on 13/07/2017.
 */
object HtmlUtils {

    @JvmStatic
    fun extractFromHtml(html: String): CharSequence {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            return noTrailingWhiteLines(Html.fromHtml(html))
        }
    }

    @JvmStatic
    private fun noTrailingWhiteLines(text: CharSequence): CharSequence {
        var text = text

        while (text[text.length - 1] == '\n') {
            text = text.subSequence(0, text.length - 1)
        }
        return text
    }
}