package com.tsci.coroutinesbus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.tsci.coroutinesbus.R
import com.tsci.coroutinesbus.core.EventBus
import com.tsci.coroutinesbus.event.Event


class ThirdFragment : Fragment() {

    // data to update, i did not use viewModel
    // so i declared variable here to update it
    // with TextWatcher callbacks.
    var str = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners(){
        requireView().findViewById<EditText>(R.id.etData)
            .doAfterTextChanged {
                str = it.toString()
            }
        requireView().findViewById<AppCompatButton>(R.id.btnSendData)
            .setOnClickListener {
                EventBus.get().post(event = Event.DataEvent(str))
                EventBus.get().post(event = Event.NameEvent("Burak"))
            }
    }
}