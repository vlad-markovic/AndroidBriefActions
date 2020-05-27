package com.vladmarkovic.briefactions.message

import androidx.annotation.StringRes
import com.vladmarkovic.briefactions.message.TextOrResource.Resource
import com.vladmarkovic.briefactions.message.TextOrResource.Text

/**
 * Created by Vlad Markovic on 13 May 2020
 */
data class CallToAction(val label: TextOrResource, val listener: () -> Unit) {

    constructor(label: String, listener: () -> Unit) : this(Text(label), listener)

    constructor(@StringRes labelRes: Int, listener: () -> Unit) : this(Resource(labelRes), listener)
}