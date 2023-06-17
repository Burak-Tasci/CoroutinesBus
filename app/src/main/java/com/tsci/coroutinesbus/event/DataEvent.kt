package com.tsci.coroutinesbus.event

/**
 * Created by tasci on 7.06.2023.
 */
data class DataEvent(
    val data: String
): BaseEvent() {

    companion object{
        const val TAG = "TAG_DATA"
    }
}