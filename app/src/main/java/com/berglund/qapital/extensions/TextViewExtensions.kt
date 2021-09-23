package com.berglund.qapital.extensions

import android.text.Html
import android.widget.TextView

fun TextView.setHtmlText(html: String) {
    text = Html.fromHtml(html)
}