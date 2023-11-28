package com.example.my_ecomerge_app.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.ActivityHomeAcitivityBinding
import com.example.my_ecomerge_app.view.fragment.HomeFragment.HomeFragment
import com.example.my_ecomerge_app.view.fragment.Notification.NotificationFragment
import com.example.my_ecomerge_app.view.fragment.GioHangFragment.CartFragment
import com.example.my_ecomerge_app.view.fragment.UserFragment.UserFragment


class HomeAcitivity : AppCompatActivity() {

    private val homeBinding by lazy {
        ActivityHomeAcitivityBinding.inflate(layoutInflater)
    }
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(homeBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            controller?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            window.statusBarColor = R.color.colorPrimaryDark  // Màu bạn muốn sử dụng
        }

        setFragment(HomeFragment.newInstance())
        homeBinding.apply {
            bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.home_1))
            bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.checkout))
            bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.bell))
            bottomNavigation.add(MeowBottomNavigation.Model(4, R.drawable.user_1))
            bottomNavigation.backgroundBottomColor = Color.WHITE
        }

        homeBinding.bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> setFragment(HomeFragment.newInstance())
                2 -> setFragment(CartFragment.newInstance())
                3 -> setFragment(NotificationFragment.newInstance())
                4 -> setFragment(UserFragment.newInstance())

                else -> " "
            }
        }
        homeBinding.bottomNavigation.show(1, true)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.framelayout, fragment, "HomeActivity")
            .commit()
    }
}