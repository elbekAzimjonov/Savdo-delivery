package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Data
import com.imsoft.savdodelivery.presentation.adapters.CustomerAdapter
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.domain.Status
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import javax.inject.Inject

class NewOrdersFragment : Fragment() {
    private lateinit var ordersNotFound: TextView
    private lateinit var newOrderRecycler: RecyclerView
    private lateinit var views: View
    private lateinit var orderAdapters: CustomerAdapter
    private lateinit var loadProgress: ProgressBar

    @Inject
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectNewOrders(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_new_orders, container, false)
        onBindViews()
        return views
    }

    private fun onBindViews() {
        ordersNotFound = views.findViewById(R.id.ordersNotFound)
        loadProgress = views.findViewById(R.id.loadProgress)
        newOrderRecycler = views.findViewById(R.id.newOrderRecycler)
        mainViewModel.getNewOrders("1").observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val list = it.data!!.data
                    orderAdapters =
                        CustomerAdapter(list, object : CustomerAdapter.OnItemClickListener {
                            override fun onItemClick(
                                orderList: Data,
                                position: Int,
                                enter: ImageView
                            ) {

                                var bundle: Bundle
                                bundle = Bundle()
                                bundle = bundleOf("OrderData" to orderList)
                                findNavController().navigate(
                                    R.id.action_customerFragment_to_mapsFragment,
                                    bundle
                                )
                            }
                        })
                    newOrderRecycler.adapter = orderAdapters
                    loadProgress.visibility = View.GONE

                    if (list.isEmpty()) {
                        ordersNotFound.visibility = View.VISIBLE
                        newOrderRecycler.visibility = View.GONE
                    } else {
                        ordersNotFound.visibility = View.GONE
                        newOrderRecycler.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    loadProgress.visibility = View.GONE
                }
            }
        }
    }
}