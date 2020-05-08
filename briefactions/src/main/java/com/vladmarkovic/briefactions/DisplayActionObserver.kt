package com.vladmarkovic.briefactions

import androidx.lifecycle.Observer

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
class DisplayActionObserver<A : Display.Action>(private val action: (Display.Action) -> Unit) : Observer<A> {
    override fun onChanged(action: A?) {
        action?.let { this.action(it) }
    }
}