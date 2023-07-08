package com.imsoft.savdodelivery.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.imsoft.savdodelivery.R

class CustomerFragment : Fragment() {
    private lateinit var onBack: ImageView
    private lateinit var newOrders: Button
    private lateinit var myOrders: Button
    private  var tabStatus = true
    private lateinit var views: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_customer, container, false)
        newOrders = views.findViewById(R.id.newOrders)
        myOrders = views.findViewById(R.id.myOrders)
        onBack = views.findViewById(R.id.customerBack)

        onBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val newOrdersFragment = NewOrdersFragment()
        val myOrdersFragment = MyOrdersFragment()
        if (tabStatus){
            makeCurrentFragment(newOrdersFragment)
        }else{
            makeCurrentFragment(myOrdersFragment)
        }

        newOrders.setOnClickListener {
            makeCurrentFragment(newOrdersFragment)
            tabStatus = true
            newOrders.setBackgroundResource(R.drawable.active_button)
            newOrders.setTextColor(Color.parseColor("#FFFFFFFF"))
            myOrders.setBackgroundResource(R.drawable.inactive_button)
            myOrders.setTextColor(Color.parseColor("#4447E2"))
        }
        myOrders.setOnClickListener {
            makeCurrentFragment(myOrdersFragment)
            tabStatus = false
            myOrders.setBackgroundResource(R.drawable.active_button)
            myOrders.setTextColor(Color.parseColor("#FFFFFFFF"))
            newOrders.setBackgroundResource(R.drawable.inactive_button)
            newOrders.setTextColor(Color.parseColor("#4447E2"))
        }
        return views
    }

    override fun onStart() {
        super.onStart()
        val newOrdersFragment = NewOrdersFragment()
        val myOrdersFragment = MyOrdersFragment()
        if (tabStatus){
            makeCurrentFragment(newOrdersFragment)
            newOrders.setBackgroundResource(R.drawable.active_button)
            newOrders.setTextColor(Color.parseColor("#FFFFFFFF"))
            myOrders.setBackgroundResource(R.drawable.inactive_button)
            myOrders.setTextColor(Color.parseColor("#4447E2"))
        }else{
            makeCurrentFragment(myOrdersFragment)
            myOrders.setBackgroundResource(R.drawable.active_button)
            myOrders.setTextColor(Color.parseColor("#FFFFFFFF"))
            newOrders.setBackgroundResource(R.drawable.inactive_button)
            newOrders.setTextColor(Color.parseColor("#4447E2"))
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, fragment)
            commit()
        }
    }
}