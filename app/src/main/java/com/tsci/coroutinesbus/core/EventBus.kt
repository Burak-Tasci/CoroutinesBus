package com.tsci.coroutinesbus.core

import com.tsci.coroutinesbus.core.EventBus.bus
import com.tsci.coroutinesbus.event.BaseEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * [EventBus] is a pipeline that receives events. It triggers
 * subscriptions if any posted event subscribed the posted event's tag.
 * It can be used in different scopes, developer can pass desired
 * scope in [EventBus.subscribe] method.
 *
 *      // Subscribing an event
 *      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *           super.onViewCreated(view, savedInstanceState)
 *           EventBus.subscribe<DataEvent>(
 *               scope = CoroutineScope(Dispatchers.IO),
 *               tag = EventType.TAG_DATA
 *           ){
 *               data = it.data
 *            }
 *
 *      ...
 *
 *      // Posting an event
 *      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *           super.onViewCreated(view, savedInstanceState)
 *           EventBus.post(event = DataEvent(str), tag = EventType.TAG_DATA)
 *
 *      ...
 *
 * Look for [DataEvent][com.tsci.coroutinesbus.event.DataEvent] for passed event in the example above.
 *
 *
 * Created by tasci on 9.06.2023.
 */
object EventBus {


    private val _bus = MutableSharedFlow<Pair<String,BaseEvent>>()
    val bus = _bus.asSharedFlow()

    // scope to use for data emitting.
    private val ioScope = CoroutineScope(Dispatchers.IO)


    /**
     * Sends data to shared flow which named [bus]. Bus contains
     * a pair of [tag] and [event] data.
     *
     * @param tag event's id
     * @param event the event to send
     */
    fun post(tag: String, event: BaseEvent) {
        val value = Pair(tag ,event)
        ioScope.launch {
             _bus.emit(value)
         }
    }

    /**
     * Subscribes the event tagged by passed [tag]. Uses default coroutine
     * scope if any [scope] not passed. Invokes higher order function
     * if tagged event triggered and its type compromises with triggered event.
     *
     * @param scope defines the scope that [action] will launch
     * @param tag event's tag to filter with other events
     * @param action higher order function to be invoked if event triggered
     */
    inline fun <reified T : BaseEvent> subscribe(
        scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
        tag: String,
        crossinline action: (T) -> Unit
    ) {
        scope.launch {
            bus.collectLatest {
                if (tag == it.first && it.second is T)
                    action(it.second as T)
            }

        }
    }
}
