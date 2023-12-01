package com.example.my_ecomerge_app.view.activity.BillActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.ActivityBillBinding

class BillActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBillBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.swButton.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked){
                binding.linearLayout5.visibility = View.VISIBLE
                binding.tvPickThat.visibility = View.GONE
            } else {
                binding.linearLayout5.visibility = View.GONE
                binding.tvPickThat.visibility = View.VISIBLE
            }

        }

    }


}