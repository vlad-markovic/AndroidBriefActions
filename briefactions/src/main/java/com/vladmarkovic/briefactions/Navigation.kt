package com.vladmarkovic.briefactions

/**
 * Created by Vladimir Markovic on 08 May 2020.
 *
 * Type used to scope brief actions for navigation away from the view - navigation actions,
 * such as starting a new Activity or a Fragment.
 */
interface Navigation {
    interface Action : BriefAction
}