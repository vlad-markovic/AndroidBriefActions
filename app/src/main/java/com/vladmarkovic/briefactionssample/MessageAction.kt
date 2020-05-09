package com.vladmarkovic.briefactionssample

import com.vladmarkovic.briefactions.DisplayAction

/**
 * Created by Vlad Markovic on 09 May 2020
 *
 * Use sealed classes to scope common actions, which helps ensuring all are handled.
 * Otherwise, if using an interface use else clause to throw an exception with message "Unhandled action: $action".
 */
sealed class MessageAction : DisplayAction {
    data class ShowMessage(val message: String) : MessageAction()
    data class ShowToast(val message: String) : MessageAction()
}