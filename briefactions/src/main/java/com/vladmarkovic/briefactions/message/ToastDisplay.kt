package com.vladmarkovic.briefactions.message

import android.widget.Toast
import androidx.annotation.StringRes
import com.vladmarkovic.briefactions.BriefActionHandler
import com.vladmarkovic.briefactions.message.TextOrResource.Resource
import com.vladmarkovic.briefactions.message.TextOrResource.Text

/**
 * Created by Vlad Markovic on 13 May 2020
 *
 * Decorate your View (Activity, Fragment, etc.) with this interface in order to have automatic handling of showing Toasts.
 */
interface ToastDisplay : BriefActionHandler {

    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        showToast(Text(message), duration)
    }

    fun showToast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        showToast(Resource(messageRes), duration)
    }

    fun showToast(message: TextOrResource, duration: Int = Toast.LENGTH_SHORT) {
        if (duration !in arrayOf(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)) {
            throw IllegalArgumentException("duration has to be either Toast.LENGTH_SHORT or Toast.LENGTH_LONG.")
        }

        getContext()?.let { context ->
            when (message) {
                is Text -> Toast.makeText(context, message.text, duration).show()
                is Resource -> Toast.makeText(context, message.stringRes, duration).show()
            }
        }
    }
}