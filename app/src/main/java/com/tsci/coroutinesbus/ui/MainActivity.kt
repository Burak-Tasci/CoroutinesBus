package com.tsci.coroutinesbus.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsci.coroutinesbus.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, FirstFragment())
            .commit()
    }
}