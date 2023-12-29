package com.example.my_ecomerge_app.view.activity.BillActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.ActivityBillBinding
import com.example.my_ecomerge_app.view.activity.Bill_Second_Activity
import com.example.my_ecomerge_app.view.adapter.AdapterBill
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
//import vn.momo.momo_partner.AppMoMoLib
//import vn.momo.momo_partner.MoMoParameterNamePayment
import java.util.Locale


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class BillActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBillBinding.inflate(layoutInflater) }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var myLocation : String
    lateinit var myPrice : String

    private var amount = "10000"
    private val fee = "0"
    var environment = 0 //developer default

    private val merchantName = "Thanh toán đơn hàng"
    private val merchantCode = "SCB01"
    private val merchantNameLabel = "Lê Văn Hiếu"
    private val description = "Thanh toán dịch vụ mua hàng trực tuyến thông qua momo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
//        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        getLocation()

        binding.swButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                binding.linearLayout5.visibility = View.VISIBLE
                binding.tvPickThat.visibility = View.GONE
            } else {
                binding.linearLayout5.visibility = View.GONE
                binding.tvPickThat.visibility = View.VISIBLE
            }
        }

        binding.btnCheckout.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).launch {
                delay(2000)
                val mList = MyApplication.appDatabase.cartDao().getAllNameCart()
                val resultString = mList.joinToString(separator = ", ")
                val size = mList.size
                Log.d("---","$mList")
                val intent = Intent(this@BillActivity, Bill_Second_Activity::class.java)
                val bundle = Bundle()
                bundle.putString("listName",resultString)
                bundle.putString("size", size.toString())
                bundle.putString("location", myLocation)
                bundle.putString("price", myPrice)
                intent.putExtras(bundle)
                withContext(Dispatchers.Main){
                    binding.progressCircular.visibility = View.GONE
                    startActivity(intent)
                }
            }
        }
        binding.btnBackBill.setOnClickListener {
            finish()
        }

        binding.tvContact.setOnClickListener {
            openContact()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val mList = MyApplication.appDatabase.cartDao().getAllCart()
            val adapter = AdapterBill(mList.toMutableList(), this@BillActivity)
            with(binding) {
                rcvBill.adapter = adapter
                rcvBill.layoutManager =
                    LinearLayoutManager(this@BillActivity, LinearLayoutManager.VERTICAL, false)
            }
            myPrice = adapter.calculateTotalPrice()
            binding.tvPrice.text = adapter.calculateTotalPrice()
        }


        binding.changeAddessDelivery.setOnClickListener {
            showEditTextAlertDialog()
        }
    }

    private fun showEditTextAlertDialog() {
        val builder = AlertDialog.Builder(this)

        // Sử dụng LayoutInflater để inflate layout của AlertDialog
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.edit_address_delivery, null)

        // Lấy ra EditText từ layout của AlertDialog
        val editText = dialogView.findViewById<EditText>(R.id.editText)

        // Thiết lập layout và tiêu đề cho AlertDialog
        builder.setView(dialogView)
            .setTitle("Nhập địa chỉ giao hàng")
            .setPositiveButton("OK") { dialog, which ->
                // Xử lý khi nhấn nút OK
                val address = editText.text.toString()
                // Thực hiện các thao tác với địa chỉ được nhập
                binding.tvLocation.text = address
            }
            .setNegativeButton("Hủy") { dialog, which ->
                // Xử lý khi nhấn nút Hủy
                dialog.dismiss()
            }

        // Tạo và hiển thị AlertDialog
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    @SuppressLint("IntentReset")
    private fun openContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        intent.type = CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(intent, 1)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            100
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 100) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        myLocation = "${list?.get(0)?.getAddressLine(0)}"
                        binding.tvLocation.text = "${list?.get(0)?.getAddressLine(0)}"
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1 && resultCode === RESULT_OK) {
            var cursor: Cursor? = null
            try {
                val uri: Uri? = data?.data
                cursor = uri?.let {
                    contentResolver.query(
                        it,
                        arrayOf(CommonDataKinds.Phone.NUMBER,
                            CommonDataKinds.Phone.DISPLAY_NAME),
                        null,
                        null,
                        null
                    )
                }
                if (cursor != null && cursor.moveToNext()) {
                    val phone: String = cursor.getString(0)
                    val name : String = cursor.getString(1)
                    // Do something with phone, name
                    binding.edtNameOfPerson.setText(name)
                    binding.edtNumberPerSon.setText(phone)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //Get token through MoMo app
//    private fun requestPayment() {
//        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT)
//        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN)
//        if (binding.tvPrice.text != null && binding.tvPrice.text.length !== 0
//        ) amount = binding.tvPrice.text.toString()
//        val eventValue: MutableMap<String, Any> = HashMap()
//        //client Required
//        eventValue[MoMoParameterNamePayment.MERCHANT_NAME] = merchantName
//        eventValue[MoMoParameterNamePayment.MERCHANT_CODE] = merchantCode
//        eventValue[MoMoParameterNamePayment.AMOUNT] = amount
//        eventValue[MoMoParameterNamePayment.DESCRIPTION] = description
//        //client Optional
//        eventValue[MoMoParameterNamePayment.FEE] = fee
//        eventValue[MoMoParameterNamePayment.MERCHANT_NAME_LABEL] = merchantNameLabel
//
//        //client call webview
//        eventValue[MoMoParameterNamePayment.REQUEST_ID] =
//            merchantCode + "-req-28nyli" + System.currentTimeMillis()
//        eventValue[MoMoParameterNamePayment.PARTNER_CODE] = merchantCode
//        val objExtraData = JSONObject()
//        try {
//            objExtraData.put("site_code", "008")
//            objExtraData.put("site_name", "CGV Cresent Mall")
//            objExtraData.put("screen_code", 0)
//            objExtraData.put("screen_name", "Special")
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3")
//            objExtraData.put("movie_format", "2D")
//            objExtraData.put(
//                "ticket",
//                "{\"ticket\":{\"01\":{\"type\":\"std\",\"price\":110000,\"qty\":3}}}"
//            )
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        eventValue[MoMoParameterNamePayment.EXTRA_DATA] = objExtraData.toString()
//        eventValue[MoMoParameterNamePayment.REQUEST_TYPE] = "payment"
//        eventValue[MoMoParameterNamePayment.LANGUAGE] = "vi"
//        eventValue[MoMoParameterNamePayment.EXTRA] = ""
//        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue)
//    }


}