package com.vladmarkovic.briefactions.message

import android.view.View.OnClickListener
import androidx.annotation.StringRes
import com.vladmarkovic.briefactions.message.TextOrResource.Resource
import com.vladmarkovic.briefactions.message.TextOrResource.Text

/**
 * Created by Vlad Markovic on 13 May 2020
 */
data class ViewAction(val label: TextOrResource, val listener: OnClickListener) {

    constructor(label: String, action: OnClickListener) : this(Text(label), action)

    constructor(@StringRes labelRes: Int, action: OnClickListener) : this(Resource(labelRes), action)
}