package com.moisesborges.tvaddict.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Created by moises.anjos on 13/07/2017.
 */
object HtmlUtils {

    @JvmStatic
    fun extractFromHtml(html: String): Spanned {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            return Html.fromHtml(html)
        }
    }
}