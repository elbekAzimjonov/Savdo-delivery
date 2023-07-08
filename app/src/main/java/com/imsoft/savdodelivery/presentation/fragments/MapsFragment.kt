package com.imsoft.savdodelivery.presentation.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.imsoft.savdodelivery.R
import com.imsoft.savdodelivery.data.model.Data
import com.imsoft.savdodelivery.data.model.Order
import com.imsoft.savdodelivery.presentation.adapters.OrderAdapters
import com.imsoft.savdodelivery.di.app.App
import com.imsoft.savdodelivery.data.domain.Status
import com.imsoft.savdodelivery.core.service.LocationService
import com.imsoft.savdodelivery.presentation.viewModel.MainViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@SuppressLint("MissingPermission")
class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {
    private lateinit var liveLocation: MutableLiveData<Location>
    private lateinit var mMap: GoogleMap
    private lateinit var views: View
    private lateinit var acceptedOrder: LinearLayout
    private lateinit var data: Data
    private lateinit var acceptedText: TextView
    private lateinit var separated: ArrayList<String>
    private lateinit var customerLat: LatLng
    private lateinit var mapBack: ImageView
    private lateinit var customName: TextView
    private lateinit var mapTime: TextView
    private lateinit var mapOrderPrice: TextView
    private lateinit var mapOrderDate: TextView
    private lateinit var orderNumber: TextView
    private lateinit var mapCall: ImageView
    private lateinit var orderListBtn: ImageView
    private lateinit var orderAdapters: OrderAdapters
    private lateinit var orderList: ArrayList<Order>
    private lateinit var locationManager: LocationManager
    private var userLocationMarker: Marker? = null
    private var startZoom: Boolean = true

    @Inject
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectMap(this)
        onBind()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_maps, container, false)
        onBindView()
        arguments?.let { bundle ->
            data = bundle.getParcelable("OrderData")!!
        }
        customName.text = data.customer_name
        orderNumber.text = "Buyurtma № ${data.id}"
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date = df.parse(data.date)!! // converting String to date
        val n = 3
        val compileDate = removeLastNchars(df.format(date), n)
        val finalDate = compileDate.slice(0..10)
        val timeDate = compileDate.slice(10..15)
        mapTime.text = timeDate
        mapOrderDate.text = finalDate
        mapOrderPrice.text = data.total_price.BOB.toString()
        separated = ArrayList()
//        separated = data.coordinate.split(",".toRegex()) as ArrayList<String>
//        separated[0]
//        separated[1]
        orderList = ArrayList()
        orderList.addAll(data.order_list)
        if (data.agent_status == "1") {
            acceptedText.text = "Buyurmani tugatish"

        } else if (data.agent_status == "0") {
            acceptedText.text = "Buyurtmani Qabul qilish"

        }
        var agentStatus = data.agent_status
        acceptedOrder.setOnClickListener {
            if (agentStatus == "1") {
                mainViewModel.finishOrder("${data.id}").observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            acceptedText.text = "Buyurtma Tugatilyabdi"
                        }
                        Status.SUCCESS -> {
                            Toast.makeText(
                                requireActivity(),
                                "Buyurtma Tugatildi!",
                                Toast.LENGTH_SHORT
                            ).show()
                            findNavController().popBackStack()
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                requireActivity(),
                                "Oops! Nimadir hato ketdi!",
                                Toast.LENGTH_SHORT
                            ).show()
                            acceptedText.text = "Buyurmani tugatish"
                        }
                    }
                }

            } else if (agentStatus == "0") {
                mainViewModel.acceptedOrder("${data.id}").observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.LOADING -> {
                            acceptedText.text = "Buyurtma qabul qilinyabdi"
                        }
                        Status.SUCCESS -> {
                            Toast.makeText(
                                requireActivity(),
                                "Buyurtma qabul qilindi!",
                                Toast.LENGTH_SHORT
                            ).show()
                            agentStatus = "1"
                            acceptedText.text = "Buyurmani tugatish"
                        }
                        Status.ERROR -> {
                            Toast.makeText(
                                requireActivity(),
                                "Oops! Nimadir hato ketdi!",
                                Toast.LENGTH_SHORT
                            ).show()
                            acceptedText.text = "Buyurtmani Qabul qilish"
                        }
                    }
                }
            }
        }
        return views
    }

    private fun setUserLocationMarker(location: Location, zoom: Float) {
        val latLng = LatLng(location.latitude, location.longitude)
        if (userLocationMarker == null) {
            val orderMarkerOption = MarkerOptions()
            try {

                // kegin qo'yiladi
//                customerLat = LatLng(separated[0].toDouble(), separated[1].toDouble())
//                orderMarkerOption.position(customerLat)
//                orderMarkerOption.icon(
//                    bitmapFromVector(
//                        requireActivity(),
//                        R.drawable.ic_order_marker
//                    )
//                )
                //val middleMost = LatLng(40.48027408949047, 71.71870465701319)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.icon(
                    bitmapFromVector(requireActivity(), R.drawable.ic_location_marker)
                )
                markerOptions.anchor(0.5f, 0.5f)
                userLocationMarker = mMap.addMarker(markerOptions)
//                mMap.addMarker(orderMarkerOption)
                userLocationMarker!!.isFlat = false
            } catch (e: IllegalStateException) {
            }
        } else {
            userLocationMarker!!.position = latLng
            userLocationMarker!!.setAnchor(0.5f, 0.5f)
        }
        val comPos = CameraPosition.fromLatLngZoom(latLng, zoom)
        val com = CameraPosition.builder(comPos).bearing(location.bearing).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(com))
        startZoom = false
    }

    private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        currentLocation()
    }

    private fun currentLocation(): MutableLiveData<Location> {
        liveLocation = MutableLiveData()
        val locationService = LocationService()
        locationService.requestSingleUpdate(requireContext(),
            object : LocationService.LocationCallback {
                override fun onNewLocationAvailable(
                    location: LocationService.GPSCoordinates?,
                    newLocation: Location
                ) {
                    try {
                        liveLocation.value = newLocation
                        setUserLocationMarker(newLocation, 16f)
                        initLocation()
                    } catch (e: IllegalStateException) {

                    }
                }
            })
        return liveLocation
    }

    private fun orderListDialog() {
        val dialog = Dialog(requireActivity(), R.style.OrderList)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.order_list_dialog)
        val orderListRecycler = dialog.findViewById(R.id.orderListRecycler) as RecyclerView
        val productNotFound = dialog.findViewById(R.id.productNotFound) as TextView
        if (orderList.isEmpty()) {
            productNotFound.visibility = View.VISIBLE
        } else {
            productNotFound.visibility = View.GONE
        }
        orderAdapters = OrderAdapters(orderList)
        orderListRecycler.adapter = orderAdapters
        dialog.show()
    }

    private fun initLocation() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            1F, this
        )
    }

    override fun onLocationChanged(location: Location) {
        liveLocation.value = location
        liveLocation.observeForever {
            if (liveLocation.value != null) {
                setUserLocationMarker(liveLocation.value!!, 16f)
            }
        }
    }

    private fun onBindView() {
        separated = ArrayList()
        acceptedText = views.findViewById(R.id.acceptedText)
        orderListBtn = views.findViewById(R.id.orderList)
        mapCall = views.findViewById(R.id.mapCall)
        mapBack = views.findViewById(R.id.onBackMap)
        customName = views.findViewById(R.id.customName)
        mapTime = views.findViewById(R.id.mapTime)
        mapOrderPrice = views.findViewById(R.id.mapOrderPrice)
        mapOrderDate = views.findViewById(R.id.mapOrderDate)
        orderNumber = views.findViewById(R.id.orderNumber)
        mapBack.setOnClickListener {
            findNavController().popBackStack()
        }

        mapCall.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + data.phone)
            startActivity(dialIntent)
        }
        orderListBtn.setOnClickListener {
            orderListDialog()
        }
        acceptedOrder = views.findViewById(R.id.acceptedOrder)
    }

    private fun onBind() {
        liveLocation = MutableLiveData()
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }

    private fun removeLastNchars(str: String, n: Int): String {
        return str.substring(0, str.length - n)
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap.clear()
    }
}