package com.vladmarkovic.briefactions.message

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar

/**
 * Created by Vlad Markovic on 13 May 2020
 *
 * If you create your custom Snackbar by extending the [BaseTransientBottomBar] (or any of it's children),
 * you need to decorate it with this interface and implement these functions.
 */
interface TransientBottomBar {
    fun setText(message: CharSequence)
    fun setText(@StringRes resId: Int)
    fun setAction(message: CharSequence, listener: View.OnClickListener)
    fun setAction(@StringRes resId: Int, listener: View.OnClickListener)
}