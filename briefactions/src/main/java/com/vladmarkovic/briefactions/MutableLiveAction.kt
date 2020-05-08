package com.vladmarkovic.briefactions

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
class MutableLiveAction<A : BriefAction> : LiveAction<A>() {
    public override fun setValue(action: A?) = super.setValue(action)
}