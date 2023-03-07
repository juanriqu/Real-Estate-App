package com.example.xmlrealestate.ui.screens.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.xmlrealestate.R
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.databinding.FragmentListHousesBinding
import com.example.xmlrealestate.domain.model.House
import com.example.xmlrealestate.ui.screens.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListHouseFragment : Fragment() {
    private lateinit var binding: FragmentListHousesBinding
    private lateinit var adapter: HouseAdapter
    private val viewModel: ListHouseViewModel by viewModels()

    inner class ListHousesActionsImpl : ListHousesActions {
        override fun onHouseClicked(house: House) {
            val action = ListHouseFragmentDirections.actionHomeFragmentToDetailedHouseFragment(
                house.id.toString()
            )
            findNavController().navigate(action)
        }
    }

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
                    adapter.submitList(state.houses?.sortedBy {
                        it.price
                    })
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

