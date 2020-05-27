package com.vladmarkovic.briefactions.message

import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration

/**
 * Created by Vlad Markovic on 13 May 2020
 */
data class SnackMessageDetails(val message: TextOrResource,
                               @Duration val duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
                               val action: ViewAction? = null,
                               val autoDismiss: Boolean = true,
                               val fullWidth: Boolean = true) {

    constructor(message: String,
                @Duration duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
                action: ViewAction? = null,
                autoDismiss: Boolean = true,
                fullWidth: Boolean = true)
        : this(TextOrResource.make(message), duration, action, autoDismiss, fullWidth)

    constructor(@StringRes messageRes: Int,
                @Duration duration: Int = BaseTransientBottomBar.LENGTH_SHORT,
                action: ViewAction? = null,
                autoDismiss: Boolean = true,
                fullWidth: Boolean = true)
        : this(TextOrResource.make(messageRes), duration, action, autoDismiss, fullWidth)

    companion object

    /**
     * Replaces values with passed in arguments, ignoring null arguments and replacing the supplied ones.
     */
    fun mutate(message: TextOrResource? = null,
               @Duration duration: Int? = null,
               action: ViewAction? = null,
               autoDismiss: Boolean? = null,
               fullWidth: Boolean? = null): SnackMessageDetails =
        copy(
            message = message ?: this.message,
            duration = duration ?: this.duration,
            action = action ?: this.action,
            autoDismiss = autoDismiss ?: this.autoDismiss,
            fullWidth = fullWidth ?: this.fullWidth
        )
}