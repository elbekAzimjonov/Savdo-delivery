package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.preferences.managers.SessionManager
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {
    private lateinit var views: View
    private lateinit var connectWithWe: LinearLayout
    private lateinit var loginButton: Button
    private lateinit var loadProgress: ProgressBar
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var userSession: SessionManager

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectLogin(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_login, container, false)
        onBindViews()
        onClickViews()

        return views
    }

    private fun onBindViews() {
        userSession = SessionManager(requireContext())
        loadProgress = views.findViewById(R.id.loadProgress)
        loginButton = views.findViewById(R.id.loginButton)
        phoneEditText = views.findViewById(R.id.phoneEditText)
        connectWithWe = views.findViewById(R.id.connectWithWe)
        passwordEditText = views.findViewById(R.id.passwordEditText)
    }

    private fun onClickViews() {
        loadProgress.visibility = View.VISIBLE
        loginButton.setOnClickListener {
            loadProgress.visibility = View.GONE
            findNavController().navigate(R.id.homeFragment)
        }
    }
}