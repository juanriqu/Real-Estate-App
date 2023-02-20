package com.example.dttassesmentandroid.ui.screens.home


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dttassesmentandroid.R
import com.example.dttassesmentandroid.common.Constants
import com.example.dttassesmentandroid.databinding.FragmentListHousesBinding
import com.example.dttassesmentandroid.domain.model.House
import com.example.dttassesmentandroid.ui.screens.main.MainActivity
import com.example.dttassesmentandroid.utils.Utils
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListHouseFragment : Fragment() {
    private lateinit var binding: FragmentListHousesBinding
    private lateinit var adapter: HouseAdapter
    private lateinit var locationManager: LocationManager
    private val viewModel: ListHouseViewModel by viewModels()

    inner class ListHousesActionsImpl : ListHousesActions {
        override fun onHouseClicked(house: House) {
            val action = ListHouseFragmentDirections.actionHomeFragmentToDetailedHouseFragment(
                house.id.toString(), getDistance(
                    LatLng(
                        house.latitude.toDouble(), house.longitude.toDouble()
                    )
                ).toString()
            )
            findNavController().navigate(action)
        }

        override fun giveMeDistance(house: House): Double {
            return getDistance(LatLng(house.latitude.toDouble(), house.longitude.toDouble()))
        }
    }

    /*FusedLocationProviderClient is used to get the current location of the user with a minimum sdk of 23,
    but I used LocationManager to get the current location of the user with a minimum sdk of 21 like the instructions said */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentListHousesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defaultResult()
        configRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state.error != null) {
                        Toast.makeText(
                            requireContext(),
                            Constants.ERROR_TOAST_INDICATOR + state.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    adapter.submitList(state.houses?.sortedBy { it.price })
                    defaultResult()
                }
            }
        }
        configSearchView()
    }

    private fun configSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (adapter.itemCount == 0) {
                    noResultFound()
                } else {
                    defaultResult()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterList(newText)
                if (adapter.itemCount == 0) {
                    noResultFound()
                } else {
                    defaultResult()
                }
                return false
            }
        })
        //This is to close the search view when the user clicks the back button
        binding.searchView.setOnCloseListener {
            defaultResult()
            viewModel.handleEvent(ListHouseContract.Event.OnSearchClosed)
            adapter.submitList(viewModel.uiState.value.houses)
            false
        }
    }

    private fun noResultFound() {
        binding.imageViewNoResultsFound.visibility = View.VISIBLE
        binding.textViewNoResultsFound.visibility = View.VISIBLE
        binding.textViewResultsNoFoundPerhaps.visibility = View.VISIBLE
    }

    private fun defaultResult() {
        binding.imageViewNoResultsFound.visibility = View.GONE
        binding.textViewNoResultsFound.visibility = View.GONE
        binding.textViewResultsNoFoundPerhaps.visibility = View.GONE
    }

    //This function is used get the distance between the user coordinates and the house
    private fun getDistance(houseLocation: LatLng): Double {
        val userLocation = getCurrentLocation()
        val distance = FloatArray(2)
        return if (userLocation.latitude != 0.0 && userLocation.longitude != 0.0) {
            Location.distanceBetween(
                userLocation.latitude,
                userLocation.longitude,
                houseLocation.latitude,
                houseLocation.longitude,
                distance
            )
            distance[0].toDouble() / 1000
        } else {
            0.0
        }
    }

    //    This function is used to get the current location of the user.
    private fun getCurrentLocation(): LatLng {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return if (checkLocationPermission() && isGPSEnabled) {
            //Note: To test it in emulator, you need go to google maps first and then run the app
            val location = Utils.getMostAccurateLocation(requireContext())
            LatLng(location?.latitude!!, location.longitude)
        } else {
            LatLng(0.0, 0.0)
        }
    }

    //This is to check if the user has granted the location permission.
    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun configRecyclerView() {
        adapter = HouseAdapter(ListHousesActionsImpl())
        binding.rvHouses.adapter = adapter
        binding.rvHouses.setHasFixedSize(true)
    }

    private fun configToolbar() {
        val toolBarText = (activity as MainActivity).findViewById<TextView>(R.id.toolbar_title)
        toolBarText.text = Constants.LIST_HOUSES_FRAGMENT_TITLE
    }

    override fun onResume() {
        super.onResume()
        configToolbar()
    }
}

