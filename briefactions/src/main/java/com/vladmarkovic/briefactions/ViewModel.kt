/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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