package com.vladmarkovic.briefactions

import androidx.lifecycle.Observer

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
class NavigationActionObserver<A : Navigation.Action>(private val action: (Navigation.Action) -> Unit) : Observer<A> {
    override fun onChanged(action: A?) {
        action?.let { this.action(it) }
    }
}