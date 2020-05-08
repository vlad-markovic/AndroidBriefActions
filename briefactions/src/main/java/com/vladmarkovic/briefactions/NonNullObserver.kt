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

import androidx.lifecycle.Observer

/**
 * Created by Vladimir Markovic on 08 May 2020.
 */
open class NonNullObserver<T>(private val handleChange: (T) -> Unit) : Observer<T?> {
    override fun onChanged(data: T?) {
        data?.let { handleChange(it) }
    }
}