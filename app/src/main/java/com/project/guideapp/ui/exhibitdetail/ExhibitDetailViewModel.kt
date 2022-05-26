package com.project.guideapp.ui.exhibitdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.guideapp.network.APIService
import com.project.guideapp.network.dto.ExhibitsDTO
import kotlinx.coroutines.launch

class ExhibitDetailViewModel : ViewModel() {

    private val _exhibitDetail = MutableLiveData<ExhibitsDTO>()
    val exhibitDetail: LiveData<ExhibitsDTO>
        get() = _exhibitDetail


    fun getExhibitDetail(exhibitId: String) {
        viewModelScope.launch {
            try {
                _exhibitDetail.value = APIService.retrofitService.getExhibitDetail(exhibitId)[0]
            } catch (ex: Exception) {
                println(ex)
            }
        }
    }
}