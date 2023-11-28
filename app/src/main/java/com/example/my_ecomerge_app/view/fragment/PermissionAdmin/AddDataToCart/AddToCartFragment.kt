package com.example.my_ecomerge_app.view.fragment.PermissionAdmin.AddDataToCart

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentAddToCartBinding
import com.example.my_ecomerge_app.model.CategorySpinner
import com.example.my_ecomerge_app.model.Order
import com.example.my_ecomerge_app.view.adapter.CategoryAdapter
import com.example.my_ecomerge_app.view.fragment.ShareVm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.Path


class AddToCartFragment : Fragment() {
    private val binding by lazy { FragmentAddToCartBinding.inflate(layoutInflater)}
    private var imgUriSaved: Uri? = null
    var selectedValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.btnChooseImage.setOnClickListener {
            //implicit intent
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

        val spinner: Spinner = binding.spType

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        binding.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedValue = binding.spType.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }




        binding.btnFinish.setOnClickListener {
            val nameFood = binding.edtTypeFood.text.toString()
            val price = binding.edtTypePriceOfFood.text.toString()
            val priceDouble = price.toDouble()
            val uriImage = imgUriSaved.toString()
            val describe = binding.edtDescribe.text.toString()
            val date = binding.edtTypeDate.text.toString()


            val order = Order(uriImage ,nameFood,priceDouble,describe,date, selectedValue.toString())
            CoroutineScope(Dispatchers.IO).launch {
                MyApplication.appDatabase.orderDao().insertOrder(order)
                Log.d("mydb","${MyApplication.appDatabase.orderDao().getAllItems()}")
                Log.d("---","${order}")
            }

        }
    }



    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                val imgPath = imgUri?.let { uriToPath(it) }

                if (imgPath != null) {
                    // Sử dụng đường dẫn (path) ở đây
                    binding.imgImageFood.setImageURI(imgUri)
                    Log.d("uri","$imgUri")
                    imgUriSaved = imgUri
                }
            }
        }

    private fun uriToPath(uri: Uri): String? {
        val context = requireContext().applicationContext
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return if (columnIndex == -1) {
                null
            } else {
                it.getString(columnIndex)
            }
        }
        return null
    }
}