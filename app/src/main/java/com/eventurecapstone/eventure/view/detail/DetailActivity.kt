package com.eventurecapstone.eventure.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var model: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[DetailViewModel::class.java]

        setupActionBar()
        setupTabSection()
        attachDataToView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupTabSection(){
        val tabs: TabLayout = binding.tabs
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = SectionPagerAdaptor(this)

        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewPager.isUserInputEnabled = position != 1
            }
        })
    }

    private fun attachDataToView(){
        val idStory = intent.getStringExtra("id_story") ?: "0"
        model.fetchEventById(idStory.toInt())

        model.event.observe(this){
            binding.eventTitle.text = it.title
            if (!it.pictureUrl.isNullOrBlank()){
                Glide.with(this).load(it.pictureUrl).into(binding.eventPicture)
            }
        }
    }

    companion object{
        private val TAB_TITLE = intArrayOf(
            R.string.info,
            R.string.map
        )
    }
}