package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Company
import com.imsoft.savdodelivery.presentation.adapters.HistoryAdapter
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.domain.Status
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import javax.inject.Inject

class HistoryFragment : Fragment() {
    private lateinit var historyRecycler: RecyclerView
    private lateinit var list: ArrayList<Company>
    private lateinit var back: ImageView
    private lateinit var loadProgress: ProgressBar
    private lateinit var ordersNotFound: TextView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var views: View

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectHistory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_history, container, false)
        historyRecycler = views.findViewById(R.id.historyRecycler)
        back = views.findViewById(R.id.back)
        loadProgress = views.findViewById(R.id.loadProgress)
        ordersNotFound = views.findViewById(R.id.ordersNotFound)
        list = ArrayList()
        mainViewModel.historyData("1").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val data = it.data!!.data
                    historyAdapter = HistoryAdapter(data)
                    historyRecycler.adapter = historyAdapter
                    loadProgress.visibility = View.GONE

                    if(data.isEmpty()){
                        ordersNotFound.visibility = View.VISIBLE
                        historyRecycler.visibility = View.GONE
                    }else{
                        ordersNotFound.visibility = View.GONE
                        historyRecycler.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    loadProgress.visibility = View.GONE
                }
            }
        }
        back.setOnClickListener {
            findNavController().popBackStack()
        }




        return views
    }
}