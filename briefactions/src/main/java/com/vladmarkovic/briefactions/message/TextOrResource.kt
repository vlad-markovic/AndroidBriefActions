package com.vladmarkovic.briefactions.message

import androidx.annotation.StringRes

/**
 * Created by Vlad Markovic on 13 May 2020
 *
 * Class for handling when either string or string resource can be provided.
 */
sealed class TextOrResource {

    data class Text(val text: CharSequence): TextOrResource()

    data class Resource(@StringRes val stringRes: Int): TextOrResource()

    companion object {
        fun make(string: String): TextOrResource = Text(string)
        fun make(@StringRes stringRes: Int): TextOrResource = Resource(stringRes)
    }
}