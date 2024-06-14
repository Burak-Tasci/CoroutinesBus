package com.tsci.coroutinesbus.core

import com.tsci.coroutinesbus.event.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

/**
 * [EventBus] is a pipeline that receives events. It triggers
 * subscriptions if any posted event comprehends with posted event.
 * It can be used in different scopes, developer can pass desired
 * scope in [EventBus.subscribe] method.
 *
 *      // Subscribing an event
 *      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *           super.onViewCreated(view, savedInstanceState)
 *           EventBus.subscribe<DataEvent>{
 *               data = it.data
 *            }
 *
 *      ...
 *
 *      // Posting an event
 *      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *           super.onViewCreated(view, savedInstanceState)
 *           EventBus.get().post(event = DataEvent(str))
 *
 *      ...
 *
 * Look for [DataEvent][com.tsci.coroutinesbus.event.Event.DataEvent] for
 * passed event in the example above.
 *
 *
 * Created by tasci on 9.06.2023.
 */
class EventBus private constructor() {


    private val _bus = MutableSharedFlow<Event>()
    val bus: SharedFlow<Event> = _bus.asSharedFlow()

    // scope to use for post and subscribe
    val scope: CoroutineScope = CoroutineScope(EmptyCoroutineContext)


    /**
     * Sends data to shared flow which named [bus]. Bus gets [event] data.
     *
     * @param event the event to send
     */
    fun post(event: Event) {
        scope.launch(Dispatchers.IO) {
            _bus.emit(event)
        }
    }

    /**
     * Subscribes the passed event. Uses default declared dispatcher
     * if any [dispatcher] not passed. Invokes higher order function
     * if bus gets an event and its type compromises with passed type.
     *
     * @param dispatcher defines the dispatcher that [action] will launch in
     * @param action higher order function to be invoked if event triggered
     */
    inline fun <reified T : Event> subscribe(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        crossinline action: (event: T) -> Unit
    ) {
        scope.launch(dispatcher) {
            bus.filterIsInstance<T>().collect {
                action(it)
            }
        }
    }

    companion object {

        private val instance by lazy {
            EventBus()
        }

        fun get(): EventBus = instance
    }
}
