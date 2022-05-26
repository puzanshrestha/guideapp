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

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status


    fun getExhibitDetail(exhibitId: String) {
        viewModelScope.launch {
            try {
                _status.value = "LOADING"
                _exhibitDetail.value = APIService.retrofitService.getExhibitDetail(exhibitId)[0]
                _status.value = ""
            } catch (ex: Exception) {
                println(ex)
                _status.value = "Something went wrong please try again later"
            }
        }
    }
}