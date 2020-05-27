package com.vladmarkovic.briefactions.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.vladmarkovic.briefactions.message.CustomSnackbarLayout.CustomSnackbarLayoutRes
import com.vladmarkovic.briefactions.message.CustomSnackbarLayout.CustomSnackbarView
import com.vladmarkovic.briefactions.message.TextOrResource.Resource
import com.vladmarkovic.briefactions.message.TextOrResource.Text

/**
 * Created by Vlad Markovic on 13 May 2020
 */


fun TextOrResource.getValue(context: Context): CharSequence =
    when (this) {
        is Text -> text
        is Resource -> context.getString(stringRes)
    }


// region Snackbar

// region Snackbar custom layout
fun createCustomLayoutSnackbar(parent: ViewGroup?,
                               custom: CustomSnackbarData? = null): BaseTransientBottomBar<*>? =
    if (custom != null && parent != null) {
        val content = when (custom.layout) {
            is CustomSnackbarLayoutRes -> LayoutInflater.from(parent.context).inflate(custom.layout.layoutRes, parent, false)
            is CustomSnackbarView -> custom.layout.view
        }

        val callback = custom.contentViewCallback ?: object : com.google.android.material.snackbar.ContentViewCallback {
            override fun animateContentIn(delay: Int, duration: Int) = Unit
            override fun animateContentOut(delay: Int, duration: Int) = Unit
        }

        custom.instantiator.invoke(parent, content, callback)
    } else {
        null
    }

sealed class CustomSnackbarLayout {
    data class CustomSnackbarLayoutRes(@LayoutRes val layoutRes: Int) : CustomSnackbarLayout()
    data class CustomSnackbarView(val view: View) : CustomSnackbarLayout()
}

data class CustomSnackbarData(
    val layout: CustomSnackbarLayout,
    inline val instantiator: (ViewGroup, View, com.google.android.material.snackbar.ContentViewCallback) -> BaseTransientBottomBar<*>,
    val contentViewCallback: com.google.android.material.snackbar.ContentViewCallback? = null
)
// endregion Snackbar custom layout

// region Snackbar setup with SnackMessageDetails
fun BaseTransientBottomBar<*>.setup(messageDetails: SnackMessageDetails,
                                    anchorView: View?,
                                    onSnackbarDismissed: ((BaseTransientBottomBar<*>) -> Unit)? = null): BaseTransientBottomBar<*> {
    @Suppress("UsePropertyAccessSyntax")
    val view = getView()

    if (messageDetails.fullWidth) {
        view.setPadding(0, 0, 0, 0)
        view.layoutParams = view.layoutParams.apply { width = ViewGroup.LayoutParams.MATCH_PARENT }
    }

    @Suppress("UsePropertyAccessSyntax")
    setDuration(messageDetails.duration)

    setText(messageDetails.message)

    messageDetails.action?.let { setAction(it) }

    anchorView?.let { setAnchorView(it) }

    if (messageDetails.autoDismiss) {
        view.setOnClickListener {
            dismiss()
            onSnackbarDismissed?.invoke(this)
        }
    }

    return this
}

fun BaseTransientBottomBar<*>.setText(message: TextOrResource) {
    when (this) {
        is Snackbar -> setText(message)
        is TransientBottomBar -> (this as TransientBottomBar).setText(message)
        else -> throw IllegalStateException("Decorate your custom BaseTransientBottomBar with the TransientBottomBar interface.")
    }
}

fun Snackbar.setText(message: TextOrResource) {
    when (message) {
        is Text -> setText(message.text)
        is Resource -> setText(message.stringRes)
    }
}

fun TransientBottomBar.setText(message: TextOrResource) {
    when (message) {
        is Text -> setText(message.text)
        is Resource -> setText(message.stringRes)
    }
}

fun BaseTransientBottomBar<*>.setAction(action: ViewAction) {
    when (this) {
        is Snackbar -> setAction(action)
        is TransientBottomBar -> (this as TransientBottomBar).setAction(action)
        else -> throw IllegalStateException("Decorate your custom BaseTransientBottomBar with the TransientBottomBar interface.")
    }
}

fun Snackbar.setAction(action: ViewAction) {
    when (val label = action.label) {
        is Text -> setAction(label.text, action.listener)
        is Resource -> setAction(label.stringRes, action.listener)
    }
}

fun TransientBottomBar.setAction(action: ViewAction) {
    when (val label = action.label) {
        is Text -> setAction(label.text, action.listener)
        is Resource -> setAction(label.stringRes, action.listener)
    }
}
// endregion Snackbar setup with SnackMessageDetails

// endregion Snackbar