package com.tsci.coroutinesbus.event

/**
 * It actually has no functionality. Just limits
 * developer to not create a bad formatted event.
 * Developer cannot use the event bus without
 * creating an event object inherits from [Event]
 * class.
 *
 * Created by tasci on 9.06.2023.
 */
sealed class Event {

    data class DataEvent(
        val data: String
    ) : Event()

    data class NameEvent(
        val name: String
    ) : Event()
}
