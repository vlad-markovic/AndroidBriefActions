package com.vladmarkovic.briefactions

/**
 * Created by Vladimir Markovic on 08 May 2020.
 *
 * Type used to scope brief actions to actions happening in the view (non-navigation actions)
 * such as showing a non-persistent message
 */
interface Display {
    interface Action : BriefAction
}