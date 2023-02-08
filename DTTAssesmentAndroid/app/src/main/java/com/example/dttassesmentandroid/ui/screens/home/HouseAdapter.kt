package com.example.dttassesmentandroid.ui.screens.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dttassesmentandroid.R
import com.example.dttassesmentandroid.common.Constants
import com.example.dttassesmentandroid.databinding.ItemHouseBinding
import com.example.dttassesmentandroid.domain.model.House

class HouseAdapter(
    private val actions: ListHousesActions
) : ListAdapter<House, HouseAdapter.HouseViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        return HouseViewHolder(
            actions, LayoutInflater.from(parent.context).inflate(
                R.layout.item_house, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) = with(holder) {
        val house = getItem(position)
        bind(house)
    }

    class HouseViewHolder(private val actions: ListHousesActions, view: View) :
        RecyclerView.ViewHolder(view) {
        private val binding = ItemHouseBinding.bind(view)
        fun bind(
            house: House
        ) = with(binding) {
            val url = Constants.IMAGE_RELATIVE_PATH + house.image
            val housePriceTxt = Constants.DOLLAR_CHARACTER + house.price.toString()
            val distance = actions.giveMeDistance(house)
            val distanceTxt = String.format("%.1f", distance) + Constants.KM
            if (distance != 0.0) {
                kmNumberTextView.text = distanceTxt
            } else {
                kmNumberTextView.text = Constants.NO_DISTANCE_LOCATION_DISABLED
            }
            housePrice.text = housePriceTxt
            houseZipCode.text = house.zipCode
            houseBathroomNumber.text = house.bathrooms.toString()
            houseBedroomNumber.text = house.bedrooms.toString()
            houseMetersNumber.text = house.size.toString()
            houseImageView.load(url)
            cardViewHouse.setOnClickListener {
                actions.onHouseClicked(house)
            }
        }
    }

    fun filterList(query: String?) {
        val filteredList: MutableList<House> = mutableListOf()
        for (house in currentList) {
            if (house.zipCode.contains(query.toString(), true) || house.city.contains(
                    query.toString(), true
                )
            ) {
                filteredList.add(house)
            }
        }
        submitList(filteredList)
    }

    class DiffCallback : DiffUtil.ItemCallback<House>() {
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }
}