package com.imsoft.savdodelivery.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.*
import com.imsoft.savdodelivery.data.model.AgentsData
import com.imsoft.savdodelivery.data.model.ClientsData
import com.imsoft.savdodelivery.data.model.Currency
import com.imsoft.savdodelivery.data.model.DebitItem
import com.imsoft.savdodelivery.data.model.ItemData
import com.imsoft.savdodelivery.data.model.Payment
import com.imsoft.savdodelivery.data.model.PaymentType
import com.imsoft.savdodelivery.data.model.PostDebit
import com.imsoft.savdodelivery.presentation.adapters.*
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.domain.Status
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import javax.inject.Inject


@Suppress("DEPRECATION")
class MenuFragment : Fragment() {
    private lateinit var views: View
    private lateinit var userProfile: ImageView
    private lateinit var priceContainer: LinearLayout
    private lateinit var arxivContainer: LinearLayout
    private lateinit var reportsContainer: LinearLayout
    private lateinit var newOrdersContainer: LinearLayout
    private lateinit var clientsAdapter: ClientsAdapter
    private lateinit var priceTypeAdapter: PriceTypeAdapter
    private lateinit var debtAdapter: DebtAdapter
    private lateinit var agentAdapter: AgentsAdapter
    private lateinit var paymentTypeAdapter: PaymentTypeAdapter
    private lateinit var paymentTypeName: MutableLiveData<ItemData>
    private lateinit var currenciesId: MutableLiveData<ItemData>
    private lateinit var paymentTypeList: ArrayList<PaymentType>
    private lateinit var paymentTypeFilter: ArrayList<PaymentType>
    private lateinit var paymentTypeData: List<PaymentType>
    private lateinit var agentsName: MutableLiveData<ItemData>
    private lateinit var agentList: ArrayList<AgentsData>
    private lateinit var agentFilter: ArrayList<AgentsData>
    private lateinit var agentData: List<AgentsData>
    private lateinit var clientName: MutableLiveData<ItemData>
    private lateinit var clientList: ArrayList<ClientsData>
    private lateinit var clientFilter: ArrayList<ClientsData>
    private lateinit var clientData: List<ClientsData>

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectMenu(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_menu, container, false)
        onBindViews()

        return views
    }

    @SuppressLint("SetTextI18n")
    private fun priceDialog() {
        val dialog = Dialog(requireActivity(), R.style.OrderDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.order_dialog)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val selectCustomer = dialog.findViewById(R.id.selectCustomer) as RelativeLayout
        val selectAgent = dialog.findViewById(R.id.selectAgent) as RelativeLayout
        val selectPayment = dialog.findViewById(R.id.paymentType) as RelativeLayout
        val paymentTypeText = dialog.findViewById(R.id.paymentTypeName) as TextView
        val clientsName = dialog.findViewById(R.id.clientsName) as TextView
        val agentName = dialog.findViewById(R.id.agentName) as TextView
        val debtRecycler = dialog.findViewById(R.id.debtRecycler) as RecyclerView
        val coursePrice = dialog.findViewById(R.id.kursPrice) as TextView
        val continueBtn = dialog.findViewById(R.id.continueBtn) as Button
        val paymentType = dialog.findViewById(R.id.priceType) as RecyclerView
        val closeDialog = dialog.findViewById(R.id.close_dialog) as ImageView
        val enterPrice = dialog.findViewById<EditText>(R.id.enterPrice)
        val debtProgress = dialog.findViewById(R.id.debtProgress) as ProgressBar
        val enterDescription = dialog.findViewById<EditText>(R.id.empty)
        val deptText = dialog.findViewById(R.id.textView2) as TextView
        val nestedScrollView = dialog.findViewById(R.id.nestedScrollView) as NestedScrollView
        continueBtn.setOnClickListener {
            if (currenciesId.value != null && clientName.value != null && paymentTypeName.value != null &&  agentsName.value != null && enterDescription.text.isNotEmpty()) {
                val payment =
                    Payment(
                        currenciesId.value!!.id,
                        currenciesId.value!!.name.toInt(),
                        clientName.value!!.id,
                        enterDescription.text.toString(),
                        1232,
                        paymentTypeName.value!!.id,
                        agentsName.value!!.id,
                        234,
                        1
                    )
                mainViewModel.postPayment(payment).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            debtProgress.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            Toast.makeText(
                                requireContext(),
                                "Muvoffaqqiyatli qoshildi",
                                Toast.LENGTH_SHORT
                            ).show()
                            debtProgress.visibility = View.GONE
                            dialog.dismiss()
                        }
                        Status.ERROR -> {
                            debtProgress.visibility = View.GONE
                        }
                    }
                }
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Barcha maydonlarni to'ldiring!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        mainViewModel.currencies().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    debtProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val data = it.data!!.currency
                    coursePrice.text = "${data[0].value} сўм"
                    currenciesId.postValue(ItemData(data[0].id,data[0].value))
                    priceTypeAdapter =
                        PriceTypeAdapter(data, object : PriceTypeAdapter.OnItemClickListener {
                            override fun onItemClick(currency: Currency, position: Int) {
                                coursePrice.text = "${currency.value} сўм"
                                currenciesId.postValue(ItemData(currency.id,currency.value))
                            }
                        })
                    paymentType.adapter = priceTypeAdapter
                    debtProgress.visibility = View.GONE
                }
                Status.ERROR -> {
                    debtProgress.visibility = View.GONE
                }
            }
        }
        paymentTypeName.observeForever {
            if (paymentTypeName.value != null) {
                paymentTypeText.text = paymentTypeName.value!!.name
            }
        }
        clientName.observeForever {
            if (clientName.value != null) {
                clientsName.text = clientName.value!!.name
            }

            if (clientName.value != null && agentsName.value != null) {
                val postDebit = PostDebit(clientName.value!!.id, agentsName.value!!.id)
                mainViewModel.paymentDebits(postDebit).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            debtProgress.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            val data = it.data
                            debtAdapter = DebtAdapter(data as ArrayList<DebitItem>)
                            debtRecycler.adapter = debtAdapter
                            debtRecycler.visibility = View.VISIBLE
                            deptText.visibility = View.VISIBLE
                            debtProgress.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            debtProgress.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Oops! Nimadir hato ketdi!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        agentsName.observeForever {
            if (agentsName.value != null) {
                agentName.text = agentsName.value!!.name
                val postDebit = PostDebit(clientName.value!!.id, agentsName.value!!.id)
                mainViewModel.paymentDebits(postDebit).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            debtProgress.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            val data = it.data
                            debtAdapter = DebtAdapter(data as ArrayList<DebitItem>)
                            debtRecycler.adapter = debtAdapter
                            debtRecycler.visibility = View.VISIBLE
                            deptText.visibility = View.VISIBLE
                            debtProgress.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            debtProgress.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Oops! Nimadir hato ketdi!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        enterDescription.setOnClickListener {
            nestedScrollView.postDelayed({ nestedScrollView.fullScroll(View.FOCUS_DOWN) }, 1000)
        }
        selectCustomer.setOnClickListener {
            clientDialog()
        }
        selectPayment.setOnClickListener {
            paymentTypes()
        }
        selectAgent.setOnClickListener {
            if (clientName.value == null) {
                Toast.makeText(
                    requireContext(),
                    "Avval Mijozni tanlang",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                agentDialog()
            }
        }
        enterPrice.setOnClickListener {
            nestedScrollView.postDelayed({
                nestedScrollView.scrollTo(
                    0,
                    2 * nestedScrollView.bottom / 5
                )
            }, 1000)
        }
        closeDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun reportsDialog() {
        val dialog = Dialog(requireActivity(), R.style.OrderList)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.reports_dialog)
        val startDate = dialog.findViewById(R.id.startDate) as RelativeLayout
        val endDate = dialog.findViewById(R.id.endDate) as RelativeLayout
        val selectCustomer = dialog.findViewById(R.id.selectCustomer) as RelativeLayout
        val selectAgent = dialog.findViewById(R.id.selectAgent) as RelativeLayout
        val radioGroup = dialog.findViewById(R.id.radioGroup) as RadioGroup
        val continueBtn = dialog.findViewById(R.id.continueBtn) as Button
        val closeDialog = dialog.findViewById(R.id.close_dialog) as ImageView
        closeDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun onBindViews() {
        clientList = ArrayList()
        clientData = ArrayList()
        agentsName = MutableLiveData()
        agentList = ArrayList()
        agentData = ArrayList()
        currenciesId = MutableLiveData()
        paymentTypeName = MutableLiveData()
        paymentTypeList = ArrayList()
        paymentTypeData = ArrayList()
        userProfile = views.findViewById(R.id.userProfile)
        newOrdersContainer = views.findViewById(R.id.newOrdersContainer)
        priceContainer = views.findViewById(R.id.priceContainer)
        clientName = MutableLiveData()
        reportsContainer = views.findViewById(R.id.reportsContainer)
        arxivContainer = views.findViewById(R.id.arxivContainer)
        userProfile.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_profileFragment)
        }
        newOrdersContainer.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_customerFragment)
        }
        priceContainer.setOnClickListener {
            priceDialog()
        }
        reportsContainer.setOnClickListener {
            reportsDialog()
        }
        arxivContainer.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_historyFragment)
        }
    }

    private fun clientDialog() {
        val dialog = Dialog(requireActivity(), R.style.AddAgentDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.client_dialog)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val searchView = dialog.findViewById(R.id.searchAgent) as EditText
        val clientRecycler = dialog.findViewById(R.id.spinnerRecycler) as RecyclerView
        val loadProgress = dialog.findViewById(R.id.loadProgress) as ProgressBar
        clientRecycler.addItemDecoration(divider)
        dialog.show()
        mainViewModel.clientsData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val data = it.data!!.data
                    clientData = data
                    clientsAdapter =
                        ClientsAdapter(clientData, object : ClientsAdapter.OnItemClickListener {
                            override fun onItemClick(clientsData: ClientsData, position: Int) {
                                clientName.postValue(ItemData(clientsData.id, clientsData.name))
                                dialog.dismiss()
                            }
                        })
                    clientRecycler.adapter = clientsAdapter
                    loadProgress.visibility = View.GONE
                    val productNotFound = dialog.findViewById(R.id.productNotFound) as TextView
                    if (data.isEmpty()) {
                        productNotFound.visibility = View.VISIBLE
                        clientRecycler.visibility = View.GONE
                    } else {
                        productNotFound.visibility = View.GONE
                        clientRecycler.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Oops! Nimadir hato ketdi", Toast.LENGTH_SHORT)
                        .show()
                    loadProgress.visibility = View.GONE
                }
            }
            searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filterClient(p0.toString())
                }
            })
        }
    }

    private fun paymentTypes() {
        val dialog = Dialog(requireActivity(), R.style.AddAgentDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.payment_type_dialog)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val searchView = dialog.findViewById(R.id.searchAgent) as EditText
        val clientRecycler = dialog.findViewById(R.id.spinnerRecycler) as RecyclerView
        val loadProgress = dialog.findViewById(R.id.loadProgress) as ProgressBar
        clientRecycler.addItemDecoration(divider)
        dialog.show()
        mainViewModel.paymentTypes().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val data = it.data!!.paymentTypes
                    paymentTypeData = data
                    paymentTypeAdapter = PaymentTypeAdapter(paymentTypeData,
                        object : PaymentTypeAdapter.OnItemClickListener {
                            override fun onItemClick(paymentType: PaymentType, position: Int) {
                                paymentTypeName.postValue(
                                    ItemData(
                                        paymentType.id,
                                        paymentType.name
                                    )
                                )
                                dialog.dismiss()
                            }
                        })
                    clientRecycler.adapter = paymentTypeAdapter
                    loadProgress.visibility = View.GONE
                    val productNotFound = dialog.findViewById(R.id.productNotFound) as TextView
                    if (data.isEmpty()) {
                        productNotFound.visibility = View.VISIBLE
                        clientRecycler.visibility = View.GONE
                    } else {
                        productNotFound.visibility = View.GONE
                        clientRecycler.visibility = View.VISIBLE
                    }

                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), "Oops! Nimadir hato ketdi", Toast.LENGTH_SHORT)
                        .show()
                    loadProgress.visibility = View.GONE
                }
            }
        }
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                filterPayment(p0.toString())
            }
        })
    }

    private fun agentDialog() {
        val dialog = Dialog(requireActivity(), R.style.AddAgentDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.agents_dialog)
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        val searchView = dialog.findViewById(R.id.searchAgent) as EditText
        val agentRecycler = dialog.findViewById(R.id.spinnerRecycler) as RecyclerView
        val loadProgress = dialog.findViewById(R.id.loadProgress) as ProgressBar
        agentRecycler.addItemDecoration(divider)
        dialog.show()


        mainViewModel.agentsData().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    loadProgress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    val data = it.data!!.data
                    agentData = data
                    agentAdapter =
                        AgentsAdapter(agentData, object : AgentsAdapter.OnItemClickListener {
                            override fun onItemClick(agentsData: AgentsData, position: Int) {

                                agentsName.postValue(ItemData(agentsData.id, agentsData.name))

                                dialog.dismiss()
                            }
                        })
                    agentRecycler.adapter = agentAdapter
                    loadProgress.visibility = View.GONE
                    val productNotFound = dialog.findViewById(R.id.productNotFound) as TextView
                    if (data.isEmpty()) {
                        productNotFound.visibility = View.VISIBLE
                        agentRecycler.visibility = View.GONE
                    } else {
                        productNotFound.visibility = View.GONE
                        agentRecycler.visibility = View.VISIBLE
                    }
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Oops! Nimadir hato ketdi",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    loadProgress.visibility = View.GONE
                }
            }
            searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    filterAgent(p0.toString())
                }
            })
        }
    }

    fun filterClient(fil: String) {
        clientFilter = ArrayList()
        for (item in clientData) {
            if (item.name.lowercase()
                    .contains(fil.lowercase())
            ) {
                clientFilter.add(item)
                clientsAdapter.filterList(clientFilter)
            }
        }
    }

    fun filterPayment(fil: String) {
        paymentTypeFilter = ArrayList()
        for (item in paymentTypeData) {
            if (item.name.lowercase()
                    .contains(fil.lowercase())
            ) {
                paymentTypeFilter.add(item)
                paymentTypeAdapter.filterList(paymentTypeFilter)
            }
        }
    }

    fun filterAgent(fil: String) {
        agentFilter = ArrayList()
        for (item in agentData) {
            if (item.name.lowercase()
                    .contains(fil.lowercase())
            ) {
                agentFilter.add(item)
                agentAdapter.filterList(agentFilter)
            }
        }
    }

}