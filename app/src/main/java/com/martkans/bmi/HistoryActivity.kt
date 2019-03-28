package com.martkans.bmi

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.martkans.bmi.adapters.HistoryAdapter
import com.martkans.bmi.logic.BmiResult
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var history: ArrayList<BmiResult> = ArrayList(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val sharedPref = this.getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE)

        if(sharedPref.getString(MainActivity.KEY_SHARED_PREF, null) != null){
            val typeToken = object : TypeToken<ArrayList<BmiResult>>() {}
            history = Gson().fromJson<ArrayList<BmiResult>>(sharedPref.getString(MainActivity.KEY_SHARED_PREF, ""),
                typeToken.type)
        }

        historyRV.layoutManager = LinearLayoutManager(this)
        historyRV.adapter = HistoryAdapter(history)
    }
}
