package com.eventurecapstone.eventure.view.create_event.maps

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
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.create_event.CreateEventViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment: Fragment(), OnMapReadyCallback {
    private val markerMap = mutableMapOf<Marker, Event>()
    private lateinit var model: CreateEventViewModel

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
        )[CreateEventViewModel::class.java]

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMaps: GoogleMap) {
        val placeInfo: FrameLayout? = view?.findViewById(R.id.place_info)

        setupTheme(gMaps)
        summonSubmitButton(gMaps, placeInfo)

        checkOnSwap(gMaps)
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
            if (it == UserPreference.Theme.NIGHT){
                setMapStyle(gMaps, night = true)
            } else {
                setMapStyle(gMaps, night = false)
            }
        }
    }

    private fun summonSubmitButton(gMaps: GoogleMap, placeInfo: FrameLayout?){
        placeInfo?.visibility = View.VISIBLE

        val eventListFragment = MapSubmitCoordinateFragment()

        val fragmentManager = childFragmentManager
        val existingFragment = fragmentManager.findFragmentByTag(MapSubmitCoordinateFragment::class.java.simpleName)

        fragmentManager.beginTransaction().apply {
            if (existingFragment == null){
                add(R.id.place_info, eventListFragment, MapSubmitCoordinateFragment::class.java.simpleName)
            } else {
                replace(R.id.place_info, eventListFragment, MapSubmitCoordinateFragment::class.java.simpleName)
            }
            commit()
        }
    }

    private fun checkOnSwap(gMaps: GoogleMap){
        gMaps.setOnCameraMoveListener {
            val x = gMaps.cameraPosition.target
            model.changeCoordinate(x)
        }
    }
}
