package com.vladmarkovic.briefactions.message

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration
import com.google.android.material.snackbar.Snackbar
import com.vladmarkovic.briefactions.BriefActionHandler

/**
 * Created by Vlad Markovic on 13 May 2020
 *
 * Decorate your View (Activity, Fragment, etc.) with this interface in order to have automatic handling of showing Snackbars.
 * Extend this interface and override the [createSnackbar] function if you want to use custom Snackbar.
 */
interface SnackDisplay : BriefActionHandler {

    /**
     * @param [anchorView] pass if you want to anchor to a different view then content view.
     */
    fun makeSnackbar(message: String,
                     @Duration duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
                     anchorView: View? = null,
                     custom: CustomSnackbarData? = null,
                     onSnackbarDismissed: ((BaseTransientBottomBar<*>) -> Unit)? = null): BaseTransientBottomBar<*> =
        makeSnackbar(SnackMessageDetails(message, duration = duration), anchorView, custom, onSnackbarDismissed)

    fun makeSnackbar(@StringRes messageRes: Int,
                     @Duration duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
                     anchorView: View? = null,
                     custom: CustomSnackbarData? = null,
                     onSnackbarDismissed: ((BaseTransientBottomBar<*>) -> Unit)? = null): BaseTransientBottomBar<*> =
        makeSnackbar(SnackMessageDetails(messageRes, duration = duration), anchorView, custom, onSnackbarDismissed)

    // TODO add lint warning "Snackbar created but not shown: did you forget to call show() ?"
    fun makeSnackbar(messageDetails: SnackMessageDetails,
                     anchorView: View? = null,
                     custom: CustomSnackbarData? = null,
                     onSnackbarDismissed: ((BaseTransientBottomBar<*>) -> Unit)? = null): BaseTransientBottomBar<*> =
        (getView() ?: anchorView)?.let { view ->
            val snackbar = createCustomLayoutSnackbar(view as? ViewGroup?, custom) ?: createSnackbar(view)
            snackbar.setup(messageDetails, anchorView, onSnackbarDismissed)
            return snackbar
        } ?: throw IllegalStateException("View was null when tried to show snackbar.")

    /**
     * Override this function in your custom [SnackDisplay] if using a custom Snackbar.
     * Note: text and duration set to snackbar here don't matter as they are set via the makeAndShowSnackbar function.
     */
    fun createSnackbar(view: View): BaseTransientBottomBar<*> =
        Snackbar.make(view, "", BaseTransientBottomBar.LENGTH_SHORT)
}