package com.fauzan.restoapp.Activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.HorizontalScrollView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.restoapp.Adapter.CategoryAdapter
import com.fauzan.restoapp.Adapter.RestaurantAdapter
import com.fauzan.restoapp.Model.RestoModel
import com.fauzan.restoapp.R
import com.fauzan.restoapp.ViewModel.MainViewModel
import com.fauzan.restoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initLocation()
        initCategory()
        initSuggest()
        initRecent("0")
    }

    private fun initRecent(cat:String) {
        var data: List<RestoModel>
        if (cat == "0") {
            data = mainViewModel.loadData().sortedBy { it.category }
        } else {
            data = mainViewModel.loadData().filter { it.category == cat }
        }

        binding.recyclerViewRecent.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerViewRecent.adapter = RestaurantAdapter(data)
    }

    private fun initSuggest() {
        binding.progressBarSuggest.visibility = View.VISIBLE
        binding.recyclerViewSuggest.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSuggest.adapter = RestaurantAdapter(mainViewModel.loadData())
        binding.progressBarSuggest.visibility = View.GONE
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCategory.adapter =
            CategoryAdapter(mainViewModel.loadCategory(),object : CategoryAdapter.ClickListener{
            override fun onClick(category: String) {
            initRecent(category)
            }

        })
        binding.progressBarCategory.visibility = View.GONE
    }

    private fun initLocation() {
        val adapter = ArrayAdapter(this, R.layout.spinner_item, mainViewModel.loadLocation())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpinner.adapter = adapter
    }
}