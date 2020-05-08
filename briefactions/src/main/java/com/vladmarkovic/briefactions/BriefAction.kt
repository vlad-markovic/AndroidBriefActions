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
 *
 * In Android, in combination with LiveData, ViewModel as more than what it might be on some other platforms.
 * It also provides easy component for managing state across view lifecycle events.
 * And when Activity/Fragment gets recreated, subscribing to observe LiveData gives us the latest value,
 * thus orientation changes are easy to manage.
 * But some times this is not desirable.
 * Some state delivered by certain actions are only relevant at the time when the action happened.
 * For instance, navigating to another screen, or displaying a non-persistent (usually error) message.
 * BriefAction type represents these actions.
 */
interface BriefAction