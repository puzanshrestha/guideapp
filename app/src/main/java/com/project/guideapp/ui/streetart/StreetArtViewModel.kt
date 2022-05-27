package com.project.guideapp.ui.streetart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guideapp.network.APIService
import com.project.guideapp.network.dto.ExhibitsDTO
import kotlinx.coroutines.launch
import java.util.*

class StreetArtViewModel : ViewModel() {

    private val _exhibits = MutableLiveData<List<ExhibitsDTO>>()
    val exhibits: LiveData<List<ExhibitsDTO>>
        get() = _exhibits

    private val _filteredExhibits = MutableLiveData<List<ExhibitsDTO>>()
    val filteredExhibits: LiveData<List<ExhibitsDTO>>
        get() = _filteredExhibits

    private val _searchResultsExhibits = MutableLiveData<List<ExhibitsDTO>>()
    val searchResultsExhibits: LiveData<List<ExhibitsDTO>>
        get() = _searchResultsExhibits


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
                _filteredExhibits.value = _exhibits.value
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

    fun filterArtWorks(filterKey: String): Unit {
        if (filterKey == "All") {
            _filteredExhibits.value = _exhibits.value
            return
        }
        _filteredExhibits.value =
            _exhibits.value?.filter { it.artStyle?.lowercase() == filterKey.lowercase() }
    }

    fun searchArt(keyword: String) {
        keyword.lowercase().let {
            _searchResultsExhibits.value = _exhibits.value?.filter {
                it.name.lowercase().startsWith(keyword) || it.artistName?.lowercase()
                    ?.startsWith(keyword) == true || it.location.lowercase().startsWith(keyword)
            }
        }
    }
}