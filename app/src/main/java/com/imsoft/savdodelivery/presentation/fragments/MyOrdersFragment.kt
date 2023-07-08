package com.imsoft.savdodelivery.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Data
import com.imsoft.savdodelivery.presentation.adapters.CustomerAdapter
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.domain.Status
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import retrofit2.HttpException
import javax.inject.Inject


class MyOrdersFragment : Fragment() {
    private lateinit var myOrderRecycler: RecyclerView
    private lateinit var views: View
    private lateinit var loadProgress: ProgressBar
    private lateinit var orderAdapters: CustomerAdapter
    private lateinit var ordersNotFound: TextView

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectMyOrders(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_my_orders, container, false)
        onBind()
        return views
    }

    fun onBind() {
        loadProgress = views.findViewById(R.id.loadProgress)
        ordersNotFound = views.findViewById(R.id.ordersNotFound)
        myOrderRecycler = views.findViewById(R.id.myOrderRecycler)

        try {
            mainViewModel.getMyOrders("1").observe(viewLifecycleOwner) {
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
                        myOrderRecycler.adapter = orderAdapters
                        loadProgress.visibility = View.GONE
                        if(list.isEmpty()){
                            ordersNotFound.visibility = View.VISIBLE
                            myOrderRecycler.visibility = View.GONE
                        }else{
                            ordersNotFound.visibility = View.GONE
                            myOrderRecycler.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(
                            requireActivity(),
                            "Nimadir hato ketdi",
                            Toast.LENGTH_SHORT
                        ).show()
                        loadProgress.visibility = View.GONE
                    }
                }
            }
        } catch (h: HttpException) {

        }
    }
}