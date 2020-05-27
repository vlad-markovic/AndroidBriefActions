package com.vladmarkovic.briefactions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.vladmarkovic.briefactions.message.MessageAction
import com.vladmarkovic.briefactions.message.SnackDisplay
import com.vladmarkovic.briefactions.message.ToastDisplay

/**
 * Created by Vlad Markovic on 13 May 2020
 */
interface BriefActionHandler {

    /**
     * If your scene does not contain a view (is not a subtype of Activity, Fragment, Dialog or View)
     * extend BriefActionHandler and override this function to provide a context.
     */
    fun getContext(): Context? =
        when (this) {
            is Context -> this
            is Fragment -> this.context
            is Dialog -> this.context
            is View -> this.context
            else -> null
        }

    /**
     * Used only for showing a Dialog.
     * If your scene does not contain a view (is not a subtype of Activity, Fragment, Dialog or View)
     * extend BriefActionHandler and override this function to provide a view, if using to show dialogs.
     */
    fun getView(): View? =
        when (this) {
            is Activity -> findViewById(android.R.id.content)
            is Fragment -> this.view
            is Dialog -> this.currentFocus
            is View -> this
            else -> null
        }

    /**
     * In your view connected to a [ViewModel] (base [Activity], [Fragment], [Dialog], etc.)
     *  - call this function where you observe [ViewModel.displayActions] (in [Activity.onCreate], [Fragment.onViewCreated], etc.)
     *  - override this function to handle other local [DisplayAction]s,
     *     - calling super.handleDisplayAction(action) for else case
     *     - when scoping your actions with sealed class, use "=" instead of "{}" (like here in handleMessageAction function)
     *       to provide automatic check if all actions are handled.
     *     - when scoping your actions with an interface, throw an exception for else case
     *       to provide a check if all actions are handled.
     */
    fun handleDisplayAction(action: DisplayAction) =
        when (action) {
            is MessageAction -> handleMessageAction(action)
            else -> throw IllegalArgumentException("Unhandled DisplayAction: $action")
        }

    fun handleNavigationAction(action: NavigationAction) {
        throw IllegalArgumentException("Unhandled NavigationAction: $action")
    }

    private fun handleMessageAction(action: MessageAction) =
        when (action) {
            is MessageAction.Toast -> {
                (this as ToastDisplay).showToast(action.message)
            }
            is MessageAction.BriefSnack -> {
                showBriefSnackbar(action); Unit
            }
        }

    fun showBriefSnackbar(action: MessageAction.BriefSnack): BaseTransientBottomBar<*> {
        val snackbar = (this as SnackDisplay).makeSnackbar(action.messageDetails)
        snackbar.show()
        return snackbar
    }
}