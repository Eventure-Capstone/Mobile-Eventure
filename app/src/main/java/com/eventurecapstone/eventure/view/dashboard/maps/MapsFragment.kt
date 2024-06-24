package com.eventurecapstone.eventure.view.dashboard.maps

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eventurecapstone.eventure.R
import com.eventurecapstone.eventure.data.entity.EventResult
import com.eventurecapstone.eventure.data.pref.UserPreference
import com.eventurecapstone.eventure.di.ViewModelFactory
import com.eventurecapstone.eventure.view.event_card.EventCardListFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment: Fragment(), OnMapReadyCallback {
    private lateinit var model: DashboardMapsViewModel
    private val markerMap = mutableMapOf<Marker, EventResult>()
    private var lastMarker: Marker? = null

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
        )[DashboardMapsViewModel::class.java]

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onPause() {
        super.onPause()
        model.clearEvent()
    }

    override fun onMapReady(gMaps: GoogleMap) {
        val placeInfo: FrameLayout? = view?.findViewById(R.id.place_info)

        setupTheme(gMaps)
        setupMapClick(gMaps)
        attachDataToView(gMaps)
        setupCard(gMaps, placeInfo)

        model.coordinate.observe(requireActivity()){
            val latLng = LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
            model.getEvent(latLng)
            gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                it?.latitude ?: 0.0,
                it?.longitude ?: 0.0
            ), 10f))
        }

        model.coordinate.observe(requireActivity()){
            val latLng = LatLng(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
            model.getEvent(latLng)
            gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
                it?.latitude ?: 0.0,
                it?.longitude ?: 0.0
            ), 10f))
        }

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
            if (it == UserPreference.Theme.NIGHT){
                setMapStyle(gMaps, night = true)
            } else {
                setMapStyle(gMaps, night = false)
            }
        }
    }

    private fun attachDataToView(gMaps: GoogleMap){
        model.events.observe(viewLifecycleOwner){ event ->
            event.forEach {
                val latLng = LatLng(it.latitude ?: 0.0, it.longitude ?: 0.0)
                val marker = gMaps.addMarker(MarkerOptions().position(latLng).title(it.title))
                    markerMap[marker!!] = it
            }
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

    private fun setupMapClick(gMaps: GoogleMap) {
        gMaps.setOnMapLongClickListener { latLng ->
            for (marker in markerMap.keys) {
                marker.remove()
            }
            markerMap.clear()
            lastMarker?.remove()

            val poiMarker = gMaps.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(vectorToBitmap(R.drawable.ic_location, Color.parseColor("#3DDC84")))
            )

            poiMarker?.showInfoWindow()
            lastMarker = poiMarker

            model.getEvent(latLng)
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
