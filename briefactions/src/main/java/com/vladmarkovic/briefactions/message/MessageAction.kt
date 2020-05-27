package com.vladmarkovic.briefactions.message

import androidx.annotation.StringRes
import com.vladmarkovic.briefactions.DisplayAction
import com.vladmarkovic.briefactions.message.TextOrResource.Resource
import com.vladmarkovic.briefactions.message.TextOrResource.Text

/**
 * Created by Vlad Markovic on 09 May 2020
 *
 * Use sealed classes to scope common actions, which helps ensuring all are handled.
 * Otherwise, if using an interface use else clause to throw an exception with message "Unhandled action: $action".
 */
sealed class MessageAction : DisplayAction {

    data class Toast(val message: TextOrResource) : MessageAction() {
        constructor(message: String): this(Text(message))
        constructor(@StringRes message: Int): this(Resource(message))
    }

    /**
     * Cannot be used with LENGTH_INDEFINITE.
     * With that you should use persistent live data to preserve state on configuration changes.
     */
    data class BriefSnack(val messageDetails: SnackMessageDetails) : MessageAction() {
        constructor(message: String): this(SnackMessageDetails(Text(message)))
        constructor(@StringRes message: Int): this(SnackMessageDetails(Resource(message)))
    }
}