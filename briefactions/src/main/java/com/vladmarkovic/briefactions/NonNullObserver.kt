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