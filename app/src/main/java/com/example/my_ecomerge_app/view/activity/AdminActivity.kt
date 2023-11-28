package com.example.my_ecomerge_app.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.ActivityAdminBinding
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var fAuth : FirebaseAuth


    private val adminBinding by lazy { ActivityAdminBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(adminBinding.root)

        fAuth = FirebaseAuth.getInstance()
//        adminBinding.appCompatButton.setOnClickListener{
//            fAuth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)


    }

}