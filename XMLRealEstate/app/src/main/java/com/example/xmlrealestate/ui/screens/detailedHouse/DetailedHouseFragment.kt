package com.example.xmlrealestate.ui.screens.detailedHouse

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import coil.load
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.databinding.FragmentDetailedHouseBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailedHouseFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentDetailedHouseBinding
    private lateinit var houseID: String
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private var argHouseId = Constants.HOUSE_ID_ARG

    private val viewModel: DetailedHouseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedHouseBinding.inflate(inflater, container, false)
        mapView = binding.mapViewHouseLocation
        mapView.getMapAsync(this)
        mapView.onCreate(savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTopAppBar()
        configFragmentArguments()
        with(binding) {
            binding.descriptionDetailedTextView.movementMethod = ScrollingMovementMethod()
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.handleEvent(DetailedHouseContract.Event.GetHouse(houseID.toInt()))
                    viewModel.uiState.collect {
                        val housePrice = Constants.DOLLAR_CHARACTER + it.house.price.toString()
                        housePriceDetailed.text = housePrice
                        descriptionDetailedTextView.text = it.house.description
                        houseBathroomNumberDetailed.text = it.house.bathrooms.toString()
                        houseBedroomNumberDetailed.text = it.house.bedrooms.toString()
                        houseLayersNumberDetailed.text = it.house.size.toString()
                        housePhotoDetailed.load(Constants.IMAGE_RELATIVE_PATH + it.house.image)
                        houseDistanceNumberDetailed.text = it.house.distance.toString()
                        configMap(
                            LatLng(
                                it.house.latitude.toDouble(), it.house.longitude.toDouble()
                            )
                        )
                    }
                }
            }
        }
    }

    //This method configures the map.
    private fun configMap(latLng: LatLng) {
        mapView.getMapAsync { googleMap ->
            map = googleMap
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.addMarker(
                MarkerOptions().position(latLng)
            )
            googleMap.uiSettings.isZoomControlsEnabled = false
            googleMap.uiSettings.isScrollGesturesEnabled = false
            googleMap.uiSettings.isZoomGesturesEnabled = false
            googleMap.uiSettings.isTiltGesturesEnabled = false
            googleMap.uiSettings.isRotateGesturesEnabled = false
            googleMap.uiSettings.isCompassEnabled = false
            googleMap.uiSettings.isMapToolbarEnabled = false
            googleMap.setOnMapClickListener {
                val gmmIntentUri = Uri.parse("google.navigation:q=${it.latitude},${it.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }
    }

    private fun configFragmentArguments() {
        arguments?.let {
            houseID = it.getString(argHouseId).toString()
        }
    }

    private fun configTopAppBar() {
        binding.toolbarDetailedHouse.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    //This overrides are for the map.

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    //This method is for the map but it has to be implemented but it is not used.
    override fun onMapReady(p0: GoogleMap) {
        //Required implementation but method is not used.
    }
}