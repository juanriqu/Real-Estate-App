package com.example.xmlrealestate.ui.screens.detailedHouse

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmlrealestate.domain.model.House
import com.example.xmlrealestate.domain.usecases.house.GetHouseUseCase
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
class DetailedHouseViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getHouseUseCase: GetHouseUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        DetailedHouseContract.State(
            //We put a default value because it needs to be initialized
            House(
                0, "", 45, 4, 3, 4, "", "", "", 4, 4, ""
            ), false, null
        )
    )
    val uiState: StateFlow<DetailedHouseContract.State> = _uiState

    fun handleEvent(event: DetailedHouseContract.Event) {
        when (event) {
            is DetailedHouseContract.Event.GetHouse -> {
                getHouse(event.houseId)
            }
            DetailedHouseContract.Event.OnErrorShown -> {
                _uiState.update { it.copy(error = null) }
            }
        }
    }

    private fun getHouse(houseId: Int) {
        viewModelScope.launch {
            getHouseUseCase.invoke(houseId).catch {
                _uiState.update { it.copy(error = it.error) }
            }.collect { result ->
                _uiState.update { it.copy(house = Utils.getHouseWithDistance(result, appContext)) }
            }
        }
    }
}