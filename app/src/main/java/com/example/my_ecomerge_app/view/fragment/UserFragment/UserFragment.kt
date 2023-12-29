package com.example.my_ecomerge_app.view.fragment.UserFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.databinding.FragmentUserBinding
import com.example.my_ecomerge_app.model.Features_Profile_Screen
import com.example.my_ecomerge_app.view.adapter.AdapterProfileScreen


class UserFragment : Fragment() {

    private val binding by lazy { FragmentUserBinding.inflate(layoutInflater) }


    companion object{
        fun newInstance() : UserFragment {
            return UserFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listFeatures = mutableListOf<Features_Profile_Screen>()
        listFeatures.add(Features_Profile_Screen("Đơn hàng đồ ăn"))
        listFeatures.add(Features_Profile_Screen("Tiện ích của tôi"))
        listFeatures.add(Features_Profile_Screen("Bảo hiểm của tôi"))
        listFeatures.add(Features_Profile_Screen("Mua lại"))
        listFeatures.add(Features_Profile_Screen("Bắt đầu bán"))
        listFeatures.add(Features_Profile_Screen("Đã thích"))
        listFeatures.add(Features_Profile_Screen("Shop đang theo dõi"))
        listFeatures.add(Features_Profile_Screen("Khách hàng thân thiết"))
        listFeatures.add(Features_Profile_Screen("Đã xem gần đây"))
        listFeatures.add(Features_Profile_Screen("Thiết lập tài khoản"))
        listFeatures.add(Features_Profile_Screen("Trung tâm trợ giúp"))
        listFeatures.add(Features_Profile_Screen("Trò chuyện với creator"))
        val adapter = AdapterProfileScreen(listFeatures,requireContext())
        binding.rcvProfile.adapter = adapter
        binding.rcvProfile.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }


}