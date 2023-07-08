package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Company
import com.imsoft.savdodelivery.presentation.adapters.AccountsAdapter

class AccountFragment : Fragment() {
    private lateinit var views: View
    private lateinit var onBack: ImageView
    private lateinit var accountAdapter: AccountsAdapter
    private lateinit var accountList: ArrayList<Company>
    private lateinit var accountRecycler: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_account, container, false)
        accountList = ArrayList()
        accountRecycler = views.findViewById(R.id.accountRecycler)
        accountList.add(Company("Elbek Azimjonov"))
        accountList.add(Company("Elbek7 Azimjonov"))
        onBack = views.findViewById(R.id.onBack)
        onBack.setOnClickListener {
            findNavController().popBackStack()
        }
        accountAdapter = AccountsAdapter(accountList, object : AccountsAdapter.OnItemClickListener {
            override fun onItemClick(company: Company, position: Int) {
                Toast.makeText(requireActivity(), "Replace account", Toast.LENGTH_SHORT).show()
            }
        })

        onBack.setOnClickListener {
            findNavController().popBackStack()
        }

        accountRecycler.adapter = accountAdapter
        return views
    }
}