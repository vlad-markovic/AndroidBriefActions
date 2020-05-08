package com.vladmarkovic.briefactions

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
open class ViewModel : androidx.lifecycle.ViewModel() {

    private val mutableNavigationActions = MutableLiveAction<Navigation.Action>()
    private val mutableDisplayActions = MutableLiveAction<Display.Action>()

    val navigationActions: LiveAction<Navigation.Action> = mutableNavigationActions
    val displayActions: LiveAction<Display.Action> = mutableDisplayActions

    open fun navigateTo(action: Navigation.Action) {
        mutableNavigationActions.value = action
    }

    open fun display(action: Display.Action) {
        mutableDisplayActions.value = action
    }
}