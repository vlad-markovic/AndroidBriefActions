package com.vladmarkovic.briefactionssample

import com.vladmarkovic.briefactions.NavigationAction
import com.vladmarkovic.briefactions.ViewModel
import com.vladmarkovic.briefactions.message.MessageAction.BriefSnack
import com.vladmarkovic.briefactionssample.BaseActivity.HideKeyboard

/**
 * Created by Vlad Markovic on 09 May 2020
 */
class MainViewModel : ViewModel() {

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) {
            display(BriefSnack("Please enter a message to show first."))
        } else {
            display(BriefSnack(message))
        }
        display(HideKeyboard)
    }

    fun openAnotherScreen(screenTitle: String?) {
        if (screenTitle.isNullOrBlank()) {
            display(BriefSnack("Please enter screen title first."))
        } else {
            navigateTo(AnotherScreen(screenTitle))
        }
        display(HideKeyboard)
    }

    data class AnotherScreen(val screenTitle: String) : NavigationAction
}