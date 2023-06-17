package com.tsci.coroutinesbus.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.tsci.coroutinesbus.R
import com.tsci.coroutinesbus.core.EventBus
import com.tsci.coroutinesbus.core.EventType
import com.tsci.coroutinesbus.event.DataEvent
import com.tsci.coroutinesbus.event.NameEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope

class FirstFragment : Fragment() {

    // datas to update, i did not use viewModel
    // so i declared variables here to update them
    // with events.
    private var data = ""
    private var name = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListeners()
        subscribeEvents()
    }

    private fun initView(){
        if (data.isNotBlank()){
            requireView().findViewById<TextView>(R.id.tvData).text = data
        }
        if (name.isNotBlank()){
            requireView().findViewById<AppCompatTextView>(R.id.tvName).text = name
        }
    }


    private fun initListeners(){
        requireView().findViewById<AppCompatButton>(R.id.btnSwitchFragment)
            .setOnClickListener {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, SecondFragment())
                    .addToBackStack(FirstFragment::class.java.simpleName)
                    .commit()
            }
    }

    private fun subscribeEvents(){
        EventBus.subscribe<DataEvent>(scope = CoroutineScope(Dispatchers.IO), tag = DataEvent.TAG){
            data = it.data
        }
        EventBus.subscribe<NameEvent>(scope = MainScope(), tag = EventType.TAG_NAME){
            name = it.name
        }
    }

}