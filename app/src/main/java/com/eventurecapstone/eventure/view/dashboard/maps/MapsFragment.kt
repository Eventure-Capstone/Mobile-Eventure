package com.eventurecapstone.eventure.view.dashboard.maps

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.entity.Event
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.dashboard.explorer.ExplorerViewModel
import com.eventurecapstone.eventure.view.event_card.EventCardListFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment: Fragment(), OnMapReadyCallback {
    private lateinit var model: ExplorerViewModel
    private val markerMap = mutableMapOf<Marker, Event>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[ExplorerViewModel::class.java]

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMaps: GoogleMap) {
        val placeInfo: FrameLayout? = view?.findViewById(R.id.place_info)

        setupTheme(gMaps)
        attachDataToView(gMaps)
        setupCard(gMaps, placeInfo)

        gMaps.setOnMapClickListener {
            placeInfo?.visibility = View.GONE
        }
    }

    private fun setMapStyle(gMaps: GoogleMap, night: Boolean = true) {
        try {
            val mapStyle = if (night){
                R.raw.map_style_night
            } else {
                R.raw.map_style_light
            }

            val success = gMaps.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), mapStyle))
            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e("MapsActivity", "Can't find style. Error: ", exception)
        }
    }

    private fun setupTheme(gMaps: GoogleMap){
        model.systemTheme.observe(viewLifecycleOwner){
            if (it == true){
                setMapStyle(gMaps, night = true)
            } else {
                setMapStyle(gMaps, night = false)
            }
        }
    }

    private fun attachDataToView(gMaps: GoogleMap){
        model.events.observe(viewLifecycleOwner){ event ->
            event.forEach {
                val latLng = LatLng(it.latitude, it.longitude)
                val marker = gMaps.addMarker(MarkerOptions().position(latLng).title(it.title))
                markerMap[marker!!] = it
            }
            val firstLocation = LatLng(event[0].latitude, event[0].longitude)
            gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLocation, 12f))
        }
    }

    private fun setupCard(gMaps: GoogleMap, placeInfo: FrameLayout?){
        gMaps.setOnMarkerClickListener {
            placeInfo?.visibility = View.VISIBLE

            val eventListFragment = EventCardListFragment()
            eventListFragment.showData(markerMap[it]!!)

            val fragmentManager = childFragmentManager
            val existingFragment = fragmentManager.findFragmentByTag(EventCardListFragment::class.java.simpleName)

            fragmentManager.beginTransaction().apply {
                if (existingFragment == null){
                    add(R.id.place_info, eventListFragment, EventCardListFragment::class.java.simpleName)
                } else {
                    replace(R.id.place_info, eventListFragment, EventCardListFragment::class.java.simpleName)
                }
                commit()
            }

            true
        }
    }
}
