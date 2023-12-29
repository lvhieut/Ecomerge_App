package com.example.my_ecomerge_app.view.fragment.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.my_ecomerge_app.R
import com.example.my_ecomerge_app.application.MyApplication
import com.example.my_ecomerge_app.databinding.FragmentHomeBinding
import com.example.my_ecomerge_app.model.Categories
import com.example.my_ecomerge_app.view.Interface.OnItemClick
import com.example.my_ecomerge_app.view.Interface.OnItemClickListener
import com.example.my_ecomerge_app.view.adapter.AdapterCategories
import com.example.my_ecomerge_app.view.adapter.AdapterOrder
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.ChickenFriedFragment.ChickenFriedFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.CoffeeFragment.CoffeeFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.DrinkFragment.DrinkFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.HamburgerFragment.HamburgerFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.NoodleFragment.NoodleFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.SaladFragment.SaladFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.SausageFragment.SausageFragment
import com.example.my_ecomerge_app.view.fragment.CategoryFoodFragment.TeaFragment.TeaFragment
import com.example.my_ecomerge_app.view.fragment.SeachFragment.SearchFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Collections


class HomeFragment : Fragment(), OnItemClickListener {

    private val homeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var adapter: AdapterCategories? = null
    private lateinit var categories: ArrayList<Categories>

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categories = ArrayList()
        addDataToCategories()
        adapter = AdapterCategories(categories, requireContext(), itemClickListener)
        homeBinding.rcvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeBinding.rcvCategory.adapter = adapter
        homeBinding.rcvCategory.setHasFixedSize(true)

        with(homeBinding) {
            rcvRecommended.layoutManager = GridLayoutManager(context, 2)
        }
        CoroutineScope(Dispatchers.Main).launch {
            val listOrder = MyApplication.appDatabase.orderDao().getAllItems()
            val adapter = AdapterOrder(listOrder.toMutableList(), requireContext(), 12)
//            Collections.shuffle(listOrder)
            homeBinding.rcvRecommended.adapter = adapter
            Log.d("---", "$listOrder")

            adapter.setOnItemClick(object : OnItemClick {
                override fun onItemClick(position: Int) {
                    val selectedItem = listOrder[position]
                    Toast.makeText(
                        requireContext(),
                        "Click on ${selectedItem.nameFood}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })


        }




        homeBinding.rlvSearch.setOnClickListener {
            val fragment = SearchFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.framelayout, fragment)
            transaction.addToBackStack(null)  // (Tùy chọn) cho phép quay lại fragment trước đó
            transaction.commit()
        }

        val sliderImage = homeBinding.sliderImage
        val slideModel = ArrayList<SlideModel>()
        slideModel.add(SlideModel("https://loship.vn/dist/images/cover-loship-14052020.jpg"))
        slideModel.add(SlideModel("https://marketplace.canva.com/EAFAHSSCrgg/3/0/1600w/canva-yellow-bold-typography-burger-instagram-post-a0sZQwMjlmA.jpg"))
        slideModel.add(SlideModel("https://cdn.grabon.in/gograbon/images/web-images/uploads/1618575517942/food-coupons.jpg"))
        slideModel.add(SlideModel("https://images.examples.com/wp-content/uploads/2017/11/disc-1024x681.jpg"))
        slideModel.add(SlideModel("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/pizza-gift-voucher-design-template-b40419258ecf127089a1b08835909ffe_screen.jpg?ts=1596267461"))
        slideModel.add(SlideModel("https://i.pinimg.com/564x/53/dd/c7/53ddc72191bd2813713c1e19498cf1ba.jpg"))
        slideModel.add(SlideModel("https://i.pinimg.com/564x/77/73/9d/77739dc9485d160bb54bf517a6d1fb33.jpg"))
        slideModel.add(SlideModel("https://i.pinimg.com/736x/12/92/b9/1292b9e966b0828995902805f1691edf.jpg"))

        sliderImage.setImageList(slideModel)
    }


    private fun addDataToCategories() {
        categories.add(Categories("Hamburger", R.drawable.burger))
        categories.add(Categories("Salad", R.drawable.salad))
        categories.add(Categories("Noodle", R.drawable.pho))
        categories.add(Categories("Gà Rán", R.drawable.fried_chicken))
        categories.add(Categories("Sausage", R.drawable.hotdog))
        categories.add(Categories("Coffee", R.drawable.coffee))
        categories.add(Categories("Tea", R.drawable.fruit))
        categories.add(Categories("Drink", R.drawable.cola))
    }


    val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(category: Categories) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            when (category.name) {
                "Hamburger" -> {
                    val burgerFragment = HamburgerFragment()
                    transaction.replace(R.id.framelayout, burgerFragment)
                }

                "Salad" -> {
                    val saladFragment = SaladFragment()
                    transaction.replace(R.id.framelayout, saladFragment)
                }

                "Gà Rán" -> {
                    val chickenFriedFragment = ChickenFriedFragment()
                    transaction.replace(R.id.framelayout, chickenFriedFragment)
                }

                "Noodle" -> {
                    val noodleFragment = NoodleFragment()
                    transaction.replace(R.id.framelayout, noodleFragment)
                }

                "Sausage" -> {
                    val sausageFragment = SausageFragment()
                    transaction.replace(R.id.framelayout, sausageFragment)
                }

                "Drink" -> {
                    val drinkFragment = DrinkFragment()
                    transaction.replace(R.id.framelayout, drinkFragment)
                }

                "Tea" -> {
                    val teaFragment = TeaFragment()
                    transaction.replace(R.id.framelayout, teaFragment)
                }

                "Coffee" -> {
                    val coffeeFragment = CoffeeFragment()
                    transaction.replace(R.id.framelayout, coffeeFragment)
                }


            }

            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onItemClick(category: Categories) {
        TODO("Not yet implemented")
    }


}