package com.fauzan.restoapp.Activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bumptech.glide.Glide
import com.fauzan.restoapp.Fragments.AboutFragment
import com.fauzan.restoapp.Fragments.MenuFragment
import com.fauzan.restoapp.Fragments.ReviewFragment
import com.fauzan.restoapp.Model.RestoModel
import com.fauzan.restoapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var item:RestoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        getBundle()
        setupViewPager()

    }

    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        binding.titleTxt.text=item.restaurant
        binding.locationTxt.text=item.location
        binding.typeTxt2.text=item.type
        binding.priceTxt2.text=item.price
        binding.ratingTxt2.text=item.rating
        binding.timeTxt2.text=item.time
        binding.descriptionTxt.text=item.description

        val drawableResourceId=resources.getIdentifier(item.picUrl,"drawable",packageName)

        Glide.with(this)
            .load(drawableResourceId)
            .into(binding.picDetail)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Create fragments and add arguments
        val tab1 = AboutFragment().apply {
            arguments = Bundle().apply {
                putString("description", item.description)
                putString("about", item.type)
            }
        }

        val tab2 = MenuFragment().apply {
            arguments = Bundle().apply {
                putString("menu", item.menu)
            }
        }

        val tab3 = ReviewFragment()

        // Add fragments to the adapter
        adapter.addFrag(tab1, "About")
        adapter.addFrag(tab2, "Menu")
        adapter.addFrag(tab3, "Review")

        // Set up the ViewPager with the adapter
        binding.viewpager.adapter = adapter

        // Link TabLayout with ViewPager
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }


    private class ViewPagerAdapter(fm: FragmentManager):FragmentPagerAdapter(fm){
        private val fragmentList = arrayListOf<Fragment>()
        private val fragmentTitleList = arrayListOf<String>()

        override fun getCount(): Int = fragmentList.size

        override fun getItem(position: Int): Fragment = fragmentList[position]
        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence = fragmentTitleList[position]
    }
}