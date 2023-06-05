package space.mrandika.wisnu.ui.poi.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.repository.GuideRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject

@HiltViewModel
class POIDetailViewModel @Inject constructor(private val repoPOI : POIRepository, private val repoGuide : GuideRepository) :ViewModel(){
    private val _state = MutableStateFlow(POIDetailUiState())
    val state : StateFlow<POIDetailUiState> = _state

    suspend fun getDetailPoi(id: Int){
        isLoading(true)
        isError(false)
        repoPOI.getPOI(id).collect{ result ->
            isLoading(false)
            result.onFailure {
                isError(value = true)
            }
            result.onSuccess {
                isSuccess(value = it)
            }
        }
    }
    suspend fun getGuide(id:Int){
        isLoading(true)
        isError(false)
        repoGuide.getGuide(id).collect{ result ->
            isLoading(false)
            result.onFailure {
                isError(value = true)
            }
            result.onSuccess {
                if(it.data == null ){
                    isEmpty(true)
                }else{
                    getGuideSuccess(it.data)
                }
            }
        }
    }
    private fun isLoading(value: Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun isError(value: Boolean){
        _state.update {
            it.copy(isError = value)
        }
    }
    private fun isSuccess(value : POIResponse){
        _state.update {
            it.copy(DetailResult = value)
        }
    }

    private fun isEmpty(value: Boolean){
        _state.update {
            it.copy(isEmpty = value)
        }
    }
    private fun getGuideSuccess(value:Guide){
        _state.update {
            it.copy(GuideResult = value)
        }
    }
}