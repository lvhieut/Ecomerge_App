package com.example.my_ecomerge_app.view.fragment.SeachFragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentSearchBinding
import com.example.my_ecomerge_app.model.SearchHistory
import com.example.my_ecomerge_app.view.adapter.AdapterHistory
import com.example.my_ecomerge_app.view.adapter.AdapterOrderSearch
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLocation()
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        CoroutineScope(Dispatchers.IO).launch {
            val list = MyApplication.appDatabase.searchHistoryDao().getAllSearchHistory()

            Log.d("search","${list}")
            val adapterHistory = AdapterHistory(list.toMutableList(),requireContext())
            binding.rcvHistorySearch.adapter = adapterHistory

        }

        binding.btnVoice.setOnClickListener {
            speak()
        }



        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                CoroutineScope(Dispatchers.IO).launch {
                    val listOrder = MyApplication.appDatabase.orderDao().getAllItems()
                    delay(1500)
                    val listAfterFilter = listOrder.filter { it.nameFood.contains(p0.toString().toLowerCase(Locale.ROOT).trim()) }
                    val orderSearch = AdapterOrderSearch(listAfterFilter.toMutableList(),requireContext())
                    val edtSearch = binding.edtSearch.text

                    val listSearchHistory = MyApplication.appDatabase.searchHistoryDao().getAllSearchHistory()
                    val adapterHistory = AdapterHistory(listSearchHistory.toMutableList(),requireContext())

                    withContext(Dispatchers.Main){
                        if (edtSearch.isEmpty() || edtSearch.length == 0){
                            binding.linearLayoutSearch.visibility = View.GONE
                            binding.rcvHistorySearch.adapter = adapterHistory
                            binding.linearLayoutHistory.visibility = View.VISIBLE
                            binding.edtSearch.clearFocus()
                        } else {
                            delay(1000)
                            binding.linearLayoutSearch.visibility = View.VISIBLE
                            binding.linearLayoutHistory.visibility = View.GONE
                            Log.d("hieulv", "$listAfterFilter")
                            binding.rcvItemFood.adapter = orderSearch
                            binding.rcvItemFood.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.rcvItemFood.visibility = View.VISIBLE
                            binding.edtSearch.isCursorVisible = false
                        }
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    delay(3000)
                    val searchHistory = SearchHistory(searchKeyword = p0.toString().toLowerCase(Locale.ROOT).trim())
                    MyApplication.appDatabase.searchHistoryDao().insert(searchHistory)
                }
            }
        })

        val food = resources.getStringArray(R.array.food)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, food)
        binding.edtSearch.setAdapter(arrayAdapter)
    }


    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        binding.tvLocation.text = "${list?.get(0)?.getAddressLine(0)}"
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            100
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    fun speak() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000 && resultCode == RESULT_OK){
            binding.edtSearch.setText(data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0));
        }
    }
}


