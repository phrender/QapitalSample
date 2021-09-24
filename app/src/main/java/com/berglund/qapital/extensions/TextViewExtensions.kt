package com.berglund.qapital.extensions

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.core.text.HtmlCompat

// TODO: Replace this
@Suppress("DEPRECATION") // Until fixed
fun TextView.setHtmlText(html: String) {
	text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		Html.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
	} else {
		Html.fromHtml(html)
	}
}
