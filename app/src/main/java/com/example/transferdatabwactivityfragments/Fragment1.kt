package com.example.demofragmentapp.passDataActivityToFragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demofragmentapp.passDataFragmentToActivity.DataViewModel
import com.example.transferdatabwactivityfragments.R

class Fragment1: Fragment(){

    var tv1: TextView ?= null
    var btn1: Button ?= null
    var btn2: Button ?= null
    var btn3: Button ?= null
    var listener: FragmentEventListener ?= null
    var dataViewmodel : DataViewModel ?= null

    interface FragmentEventListener{
        fun updateActivityData(msg:String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentEventListener)
            listener = context
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment1, container, false)

        initViews(view)
        setClickListeners()
        if(arguments?.getString("Message") != null) {
            tv1?.visibility = View.VISIBLE
            val msg = arguments?.getString("Message")
            tv1?.text = msg.toString()
        }
        return view
    }

    fun initViews(view: View){
        tv1 = view.findViewById(R.id.tv)
        btn1 = view.findViewById(R.id.btnClick5)
        btn2 = view.findViewById(R.id.btnClick1)
        btn3 = view.findViewById(R.id.btn3)

        dataViewmodel = ViewModelProviders.of(requireActivity()).get(DataViewModel::class.java)
    }

    fun setClickListeners(){
        btn1?.setOnClickListener {
            listener?.updateActivityData("Passed data from Fragment to Activity using Callback interfaces")
        }

        btn2?.setOnClickListener {
            if(activity is MainActivity) {
                (activity as MainActivity).shareDataWithFragment("Passed data from Fragment to Activity using Activity's Method")
            }
        }

        btn3?.setOnClickListener {
            dataViewmodel?.setActivityData("Passed data from Fragment to Activity using ViewModel")
        }

        dataViewmodel?.getFragmentData()?.observe(activity as MainActivity, Observer {
            tv1?.visibility = View.VISIBLE
            tv1?.text = it
        })
    }

    fun updateData(data : String){
        tv1?.visibility = View.VISIBLE
        tv1?.text = data
    }

}