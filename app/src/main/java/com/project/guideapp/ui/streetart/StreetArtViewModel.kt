package com.project.guideapp.ui.streetart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guideapp.network.APIService
import com.project.guideapp.network.dto.ExhibitsDTO
import kotlinx.coroutines.launch

class StreetArtViewModel : ViewModel() {

    private val _exhibits = MutableLiveData<List<ExhibitsDTO>>()
    val exhibits: LiveData<List<ExhibitsDTO>>
        get() = _exhibits

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status


    init {
        getExhibits()
    }

    private fun getExhibits() {
        viewModelScope.launch {
            try {
                _status.value = "LOADING"
                _exhibits.value = APIService.retrofitService.getExhibits()
                if (_exhibits.value!!.isEmpty())
                    _status.value = "Data not available"
                else
                    _status.value = ""

            } catch (ex: Exception) {
                _status.value = "Something went wrong please try again later"
                println(ex)

            }
        }
    }
}