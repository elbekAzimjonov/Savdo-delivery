package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.CityDataItem
import com.imsoft.savdodelivery.presentation.adapters.CompanyAdapter
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {
    private lateinit var views: View
    private lateinit var loadProgress: ProgressBar
    private lateinit var list: ArrayList<CityDataItem>
    private lateinit var recyclerView: RecyclerView
    private lateinit var companyAdapter: CompanyAdapter

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_home, container, false)
        list = ArrayList()
        loadProgress = views.findViewById(R.id.loadProgress)
        recyclerView = views.findViewById(R.id.recyclerView)
        loadProgress.visibility = View.VISIBLE
        list.add(CityDataItem("Google"))
        companyAdapter = CompanyAdapter(list, object : CompanyAdapter.OnItemClickListener {
            override fun onItemClick(company: CityDataItem, position: Int) {
                findNavController().navigate(R.id.action_homeFragment_to_menuFragment)
            }
        })
        loadProgress.visibility = View.GONE
        recyclerView.adapter = companyAdapter
        return views
    }
}