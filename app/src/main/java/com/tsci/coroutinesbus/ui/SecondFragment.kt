package com.tsci.coroutinesbus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.tsci.coroutinesbus.R
import com.tsci.coroutinesbus.core.EventBus
import com.tsci.coroutinesbus.core.EventType
import com.tsci.coroutinesbus.event.DataEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class SecondFragment : Fragment() {

    // data to update, i did not use viewModel
    // so i declared variable here to update it
    // with event
    private var data = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        subscribeEvents()
    }

    private fun initView() {
        if (data.isNotBlank()) {
            requireView().findViewById<TextView>(R.id.tvData).text = data
        }
    }

    private fun initListeners() {
        requireView().findViewById<AppCompatButton>(R.id.btnSwitchFragment)
            .setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, ThirdFragment())
                    .addToBackStack(SecondFragment::class.java.simpleName)
                    .commit()
            }
    }

    private fun subscribeEvents() {
        EventBus.subscribe<DataEvent>(
            scope = CoroutineScope(Dispatchers.IO),
            tag = EventType.TAG_DATA
        ) {
            data = it.data
        }
    }
}