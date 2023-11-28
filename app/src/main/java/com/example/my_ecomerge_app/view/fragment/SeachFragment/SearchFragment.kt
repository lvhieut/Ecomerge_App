package com.example.my_ecomerge_app.view.fragment.SeachFragment

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentSearchBinding
import com.example.my_ecomerge_app.model.SearchHistory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
            // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.btnBack.setOnClickListener{
                parentFragmentManager.popBackStack()
            }



        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Được gọi trước khi văn bản thay đổi
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Được gọi khi văn bản đang thay đổi
                val newText = s.toString() // Lấy văn bản hiện tại

            }

            override fun afterTextChanged(s: Editable?) {
                // Được gọi sau khi văn bản đã thay đổi
            }
        }
//        binding.edtSearch.addTextChangedListener(textWatcher)

        val food = resources.getStringArray(R.array.food)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, food)
        binding.edtSearch.setAdapter(arrayAdapter)

        binding.edtSearch.addTextChangedListener {object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               // Được gọi trước khi văn bản thay đổi
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                // Được gọi khi văn bản đang thay đổi
                // Xử lý văn bản thay đổi ở đây
            }

            override fun afterTextChanged(p0: Editable?) {
                // Được gọi sau khi văn bản đã thay đổi
            }
        }

        }
    }

}