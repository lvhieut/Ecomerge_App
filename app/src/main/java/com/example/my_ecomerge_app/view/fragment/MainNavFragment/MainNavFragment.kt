package com.example.my_ecomerge_app.view.fragment.MainNavFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.FragmentMainNavBinding
import com.example.my_ecomerge_app.view.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth


class MainNavFragment : Fragment() {
    private val navBinding by lazy { FragmentMainNavBinding.inflate(layoutInflater) }
    private lateinit var fAuth : FirebaseAuth

    //        adminBinding.appCompatButton.setOnClickListener{
//            fAuth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return navBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateView()
    }



    private fun initiateView() {
        fAuth = FirebaseAuth.getInstance()
        navBinding.apply {
            btnAddData.setOnClickListener {
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.addToCartFragment)
            }
            btnLogOut.setOnClickListener {
                fAuth.signOut()
                NavHostFragment.findNavController(this@MainNavFragment).navigate(R.id.action_global_loginActivity)
            }
        }
    }
}