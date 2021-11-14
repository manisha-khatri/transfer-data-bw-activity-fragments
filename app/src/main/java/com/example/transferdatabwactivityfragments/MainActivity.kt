package com.example.demofragmentapp.passDataActivityToFragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demofragmentapp.passDataFragmentToActivity.DataViewModel
import com.example.transferdatabwactivityfragments.Fragment2
import com.example.transferdatabwactivityfragments.R
import java.lang.Exception

// Pass data from, Activity --> Fragment
// 1. using bundle, on creation of fragment
// 2. passing data in the fragment method
// 3. ViewModel + Livedata


// Pass data from, Fragment --> Activity
// 1. Callback interfaces
// 2. All the Activity's methods are accessible to a fragment, so passing data in the activity method
// 3. ViewModel + Livedata


class MainActivity : AppCompatActivity(), Fragment1.FragmentEventListener, Fragment2.Fragment2EventListener {

    var btn1: Button ?= null
    var btn2: Button ?= null
    var btn3: Button ?= null
    var btn4: Button ?= null
    var tv1 : TextView ?= null
    var fragment1 = Fragment1()
    var fragment2 = Fragment2()
    var dataViewModel: DataViewModel ?= null
    var handler: Handler?= null
    var fragment2Layout :LinearLayout ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initClickListeners()
    }

    fun initViews(){
        btn1 = findViewById(R.id.btn)
        btn2 = findViewById(R.id.btn2)
        tv1 = findViewById(R.id.text)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        fragment2Layout = findViewById(R.id.fragment2Layout)

        dataViewModel = ViewModelProviders.of(this@MainActivity).get(DataViewModel::class.java) //vm
        dataViewModel?.getActivityData()?.observe(this, Observer {
            tv1?.visibility = View.VISIBLE
            tv1?.text = it
        })
        handler = Handler()

        val mgr = supportFragmentManager.beginTransaction()
        mgr.replace(R.id.fragment_placeholder, fragment1)
        mgr.commit()
    }

    fun initClickListeners(){
        btn1?.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Message", "Passed data from Activity to Fragment using Bundle")

            fragment1 = Fragment1()
            fragment1.arguments = bundle
            val mgr = supportFragmentManager.beginTransaction()
            mgr.replace(R.id.fragment_placeholder, fragment1)
            mgr.commit()
        }

        btn2?.setOnClickListener {
            fragment1.updateData("Passed data from Activity to Fragment using Fragment Method")
        }

        btn3?.setOnClickListener {
            dataViewModel?.setFragmentData("Passed data from Activity to Fragment using ViewModel")

            Thread(object : Runnable {
                override fun run() {
                    try {
                        Thread.sleep(2000)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    handler?.post(
                            object: Runnable{
                                override fun run() {
                                    dataViewModel?.setFragmentData("Passed data from Activity to Fragment using ViewModel after some interval")
                                }
                            }
                    )
                }
            }).start()
        }

        btn4?.setOnClickListener {
            fragment2Layout?.visibility = View.VISIBLE
            fragment2 = Fragment2()
            val mgr = supportFragmentManager.beginTransaction()
            mgr.add(R.id.fragment_placeholder2, fragment2)
            mgr.commit()
        }
    }

    fun shareDataWithFragment(msg: String){
        tv1?.visibility = View.VISIBLE
        tv1?.text = msg
    }

    fun shareDataFromF2ToF1(msg: String){
        fragment1.updateData(msg)
    }

    override fun updateActivityData(msg: String) {
        tv1?.visibility = View.VISIBLE
        tv1?.text = msg
    }

    override fun updateF1DataFromF2(msg: String) {
        fragment1.updateData(msg)
    }

}