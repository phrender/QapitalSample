package com.berglund.qapital.extensions

import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat

// TODO: Replace this
fun TextView.setHtmlText(html: String) {
    text = Html.fromHtml(html)
}