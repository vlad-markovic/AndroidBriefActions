package com.vladmarkovic.briefactions

import androidx.lifecycle.Observer

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
class BriefActionObserver<A : BriefAction>(private val handleAction: (BriefAction) -> Unit) : Observer<A> {
    override fun onChanged(action: A?) {
        action?.let { handleAction(it) }
    }
}