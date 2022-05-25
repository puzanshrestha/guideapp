package com.project.guideapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guideapp.network.APIService
import com.project.guideapp.network.dto.ExhibitsDTO
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _exhibits = MutableLiveData<List<ExhibitsDTO>>()
    val exhibits: LiveData<List<ExhibitsDTO>>
        get() = _exhibits

    init {
        getExhibits()
    }

    private fun getExhibits() {
        viewModelScope.launch {
            try {
                _exhibits.value = APIService.retrofitService.getExhibits()
            } catch (ex: Exception) {
                println(ex)

            }
        }
    }
}