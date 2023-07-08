package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import com.imsoft.savdodelivery.R

class ProfileFragment : Fragment() {
    private lateinit var views: View
    private lateinit var back: ImageView
    private lateinit var accountBtn: RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_profile, container, false)
        onBindViews()
        return views
    }

    private fun onBindViews() {
        back = views.findViewById(R.id.onBack)
        back.setOnClickListener {
            findNavController().popBackStack()
        }
        accountBtn = views.findViewById(R.id.multiple_accounts)

        accountBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_accountFragment)
        }
    }
}