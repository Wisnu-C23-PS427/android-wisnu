package space.mrandika.wisnu.ui.poi.poidetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.repository.POIRepository

@HiltViewModel
class PoiDetailViewModel(private val repo : POIRepository) :ViewModel(){
    private val _state = MutableStateFlow(PoiDetailUiState())
    val state : StateFlow<PoiDetailUiState> = _state

    suspend fun getDetailPoi(id: Int){
        isLoading(true)
        isError(false)
        repo.getPOI(id).collect{ result ->
            isLoading(false)
            result.onFailure {
                isError(value = false)
            }
            result.onSuccess {
                isSuccess(value = it)
            }
        }
    }
    fun isLoading(value: Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    fun isError(value: Boolean){
        _state.update {
            it.copy(isError = value)
        }
    }
    fun isSuccess(value : POIResponse){
        _state.update {
            it.copy(DetailResult = value)
        }
    }
}