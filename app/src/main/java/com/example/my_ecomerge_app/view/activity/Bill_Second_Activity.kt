package com.example.my_ecomerge_app.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.ActivityBillSecondBinding

class Bill_Second_Activity : AppCompatActivity() {

    private val binding by lazy { ActivityBillSecondBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )



        val bundle = intent.extras

        val listName = bundle?.getString("listName")
        val size = bundle?.getString("size")
        val location = bundle?.getString("location")
        val price = bundle?.getString("price")

        binding.tvCacMonAn.text = listName
        binding.tvQuanityBillSecond.text = size
        binding.tvMyLocation.text = location
        binding.tvPriceBillSecond.text = price
        binding.btnBackBillSecond.setOnClickListener {
            finish()
        }
    }
}