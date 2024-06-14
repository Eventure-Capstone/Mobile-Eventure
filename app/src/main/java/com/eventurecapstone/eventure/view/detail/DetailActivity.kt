package com.eventurecapstone.eventure.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.databinding.ActivityDetailBinding
import com.eventurecapstone.eventure.entity.Event
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var model: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[DetailViewModel::class.java]

        setupActionBar()
        setupTabSection()
        loadData()



        model.event.observe(this){
            attachPictureToView(it.pictureUrl)
        }
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
    }

    private fun attachPictureToView(url: String?){
        if (!url.isNullOrBlank()){
            Glide.with(this).load(url).into(binding.eventPicture)
        }
    }

    private fun loadData(){
        model.setEvent(Event(
            id = 1,
            title = "testing aja sih ini",
            location = "Lendah, Kulon Progo",
            latitude = -7.924970,
            longitude = 110.192390,
            startDate = "15-06-2024",
            startTime = "14.00",
            description = "lorem ipsum wae lah wkwkwk"
        ))
    }

    companion object{
        private val TAB_TITLE = intArrayOf(
            R.string.info,
            R.string.map
        )
    }
}