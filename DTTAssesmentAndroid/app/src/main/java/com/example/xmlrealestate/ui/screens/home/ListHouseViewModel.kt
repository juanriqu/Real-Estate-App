package com.example.xmlrealestate.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmlrealestate.common.Constants
import com.example.xmlrealestate.domain.usecases.house.GetAllHousesUseCase
import com.example.xmlrealestate.network.NetworkResult
import com.example.xmlrealestate.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ListHouseViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getAllHousesUseCase: GetAllHousesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListHouseContract.State(null, false, null))
    val uiState: StateFlow<ListHouseContract.State> = _uiState

    init {
        getHouses()
    }

    fun handleEvent(event: ListHouseContract.Event) {
        when (event) {
            is ListHouseContract.Event.OnSearchClosed -> {
                getHouses()
            }
            ListHouseContract.Event.GetHouses -> {
                getHouses()
            }
            ListHouseContract.Event.OnErrorShown -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun getHouses() {
        //Check if the device has internet connection
        viewModelScope.launch {
            if (Utils.hasInternetConnection(
                    context = appContext
                )
            ) {
                //In case of internet connection, we get the data from the API
                getHousesWithInternet()
            } else {
                //In case of no internet connection, we get the data from the database
                getHousesWithNoInternet()
            }
        }
    }

    private fun getHousesWithInternet() {
        viewModelScope.launch {
            getAllHousesUseCase.invoke().catch { e ->
                _uiState.update { it.copy(error = e.message) }
            }.collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                houses = Utils.calculateDistanceToHouses(
                                    result.data!!,
                                    appContext
                                )
                            )
                        }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun getHousesWithNoInternet() {
        viewModelScope.launch {
            getAllHousesUseCase.invoke().catch { e ->
                _uiState.update { it.copy(error = e.message) }
            }.collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.update {
                            it.copy(
                                houses = Utils.calculateDistanceToHouses(
                                    result.data!!,
                                    appContext
                                ),
                                isLoading = false,
                                error = Constants.NO_INTERNET_CONNECTION_ERROR
                            )
                        }
                    }
                    is NetworkResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is NetworkResult.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                }
            }
        }
    }
}