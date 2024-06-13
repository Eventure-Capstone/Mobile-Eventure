package com.eventurecapstone.eventure.view.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.eventurecapstone.eventure.view.detail.info.InfoFragment
import com.eventurecapstone.eventure.view.detail.maps.MapsFragment

class SectionPagerAdaptor(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position){
            0 -> fragment = InfoFragment()
            1 -> fragment = MapsFragment()
        }
        return fragment as Fragment
    }

}