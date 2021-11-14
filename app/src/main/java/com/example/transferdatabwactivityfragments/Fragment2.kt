package com.example.transferdatabwactivityfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.demofragmentapp.passDataActivityToFragment.Fragment1
import com.example.demofragmentapp.passDataActivityToFragment.MainActivity
import com.example.demofragmentapp.passDataFragmentToActivity.DataViewModel

class Fragment2: Fragment() {

    var btn1: Button?= null
    var btn2: Button?= null
    var btn3: Button?= null
    var listener: Fragment2EventListener ?= null
    var dataViewmodel : DataViewModel ?= null

    interface Fragment2EventListener{
        fun updateF1DataFromF2(msg:String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Fragment2EventListener)
            listener = context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment2, container, false)

        initViews(view)
        setClickListeners()

        return view
    }

    fun initViews(view: View){
        btn1 = view.findViewById(R.id.btn1)
        btn2 = view.findViewById(R.id.btn2)
        btn3 = view.findViewById(R.id.btn3)

        dataViewmodel = ViewModelProviders.of(requireActivity()).get(DataViewModel::class.java)
    }

    fun setClickListeners(){
        btn1?.setOnClickListener {
            listener?.updateF1DataFromF2("Passed data from Frag2 to Frag1 using CallBack Interfaces")
        }

        btn2?.setOnClickListener {
            if(activity is MainActivity) {
                (activity as MainActivity).shareDataFromF2ToF1("Passed data from Frag2 to Frag1 using using Activity's Method")
            }
        }

        btn3?.setOnClickListener {
            dataViewmodel?.setFragmentData("Passed data from Frag2 to Frag1 using ViewModel")
        }

    }
}