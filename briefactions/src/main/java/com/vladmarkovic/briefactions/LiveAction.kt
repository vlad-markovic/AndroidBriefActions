package com.vladmarkovic.briefactions

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Vladimir Markovic on 08 May 2020.
 *
 * Type of LiveData which:
 *  - is scoped to only accept data of type BriefAction,
 *  - allows consuming of action only once.
 *  - allows for only one observer to subscribe by default:
 *    don't show a message or navigate more then once in case of (accidental) multiple subscribers.
 *    If you want to allow multiple subscribers, you must pass true as [add] flag for observe function.
 *
 * Adapted from https://github.com/hadilq/LiveEvent/blob/master/lib/src/main/java/com/hadilq/liveevent/LiveEvent.kt
 * Named as "Action" and not "Event", as event is something we react to, and action is something we initiate.
 */
abstract class LiveAction<A: BriefAction>: LiveData<A>() {

    private val observers = ArraySet<ObserverWrapper<in A>>()

    /**
     * By default it allows for only one observer to subscribe.
     */
    override fun observeForever(observer: Observer<in A>) {
        setOrAddObserver(null, observer, false)
    }

    /**
     * @param [add] - pass as true to allow multiple observers to subscribe.
     * Note: after other observers have already subscribed, passing nothing (omitting) or false
     * for [add] param will remove previously subscribed observers.
     */
    @MainThread
    fun observeForever(observer: Observer<in A>, add: Boolean) {
        setOrAddObserver(observer = observer, add = add)
    }

    /**
     * By default it allows for only one observer to subscribe.
     */
    @MainThread
    override fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in A>) {
        setOrAddObserver(lifecycleOwner, observer, false)
    }

    /**
     * @param [add] - pass as true to allow multiple observers to subscribe.
     * Note: after other observers have already subscribed, passing nothing (omitting) or false
     * for [add] param will remove previously subscribed observers.
     */
    @MainThread
    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<in A>, add: Boolean) {
        setOrAddObserver(lifecycleOwner, observer, add)
    }

    /**
     * By default it allows for only one observer to subscribe.
     * @param [add] pass as true to allow multiple observers to subscribe.
     * Note: if observe without passing true for [add] is called after other observers have already subscribed,
     * it will remove those observer.
     */
    @MainThread
    private fun setOrAddObserver(lifecycleOwner: LifecycleOwner? = null, observer: Observer<in A>, add: Boolean) {
        synchronized(observers) {
            val observerWrapper = ObserverWrapper(observer)
            if (add) {
                if (!observers.contains(observerWrapper) && lifecycleOwner?.lifecycle?.currentState != DESTROYED) {
                    observers.add(observerWrapper)
                }
            } else {
                observers.iterator().forEach { removeObserver(it) }
                observers.add(observerWrapper)
            }

            if (lifecycleOwner == null) super.observeForever(observerWrapper)
            else super.observe(lifecycleOwner, observerWrapper)
        }
    }

    @MainThread
    override fun removeObserver(observer: Observer<in A>) {
        synchronized(observers) {
            if (!observers.remove(observer)) {
                val iterator = observers.iterator()
                while (iterator.hasNext()) {
                    if (iterator.next().observer == observer) {
                        iterator.remove()
                        break
                    }
                }
            }
            super.removeObserver(observer)
        }
    }

    @MainThread
    override fun setValue(action: A?) {
        synchronized(observers) {
            observers.forEach { it.onNewAction() }
            super.setValue(action)
        }
    }

    private class ObserverWrapper<A: BriefAction>(val observer: Observer<in A>) : Observer<A> {

        // Initialised to true not to consume any actions on subscribing.
        private val processed: AtomicBoolean = AtomicBoolean(true)

        override fun onChanged(action: A?) {
            if (!processed.getAndSet(true)) {
                action?.let { observer.onChanged(it) }
            }
        }

        fun onNewAction() {
            processed.set(false)
        }
    }
}