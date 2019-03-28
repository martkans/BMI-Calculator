package com.martkans.bmi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val history: ArrayList<String> = ArrayList()

        for (i in 1..100) {
            history.add("history #$i")
        }

        historyRV.layoutManager = LinearLayoutManager(this)
        historyRV.adapter = HistoryAdapter(history)
    }
}
