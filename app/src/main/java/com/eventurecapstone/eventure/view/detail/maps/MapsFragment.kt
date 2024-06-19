package com.eventurecapstone.eventure.view.detail.maps

import android.content.res.Resources
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.detail.DetailViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var model: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(
            requireActivity(),
            ViewModelFactory.getInstance(requireActivity())
        )[DetailViewModel::class.java]

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(gMaps: GoogleMap) {
        model.systemTheme.observe(viewLifecycleOwner){
            if (it == UserPreference.Theme.NIGHT){
                setMapStyle(gMaps, night = true)
            } else {
                setMapStyle(gMaps, night = false)
            }
        }

        model.event.observe(viewLifecycleOwner){
            val coordinate = LatLng(it.latitude, it.longitude)
            gMaps.addMarker(MarkerOptions().position(coordinate).title(it.title))
            gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 13f))
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
}