package space.mrandika.wisnu.ui.poi.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.repository.GuideRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject

@HiltViewModel
class POIDetailViewModel @Inject constructor(private val repoPOI : POIRepository, private val repoGuide : GuideRepository) :ViewModel(){
    private val _state = MutableStateFlow(POIDetailUiState())
    val state : StateFlow<POIDetailUiState> = _state

    fun getDetailPoi(id: Int){
        viewModelScope.launch {
            setError(false)
            setLoading(true)

            repoPOI.getPOI(id).collect{ result ->
                setLoading(false)
                result.onFailure {
                    setError(value = true)
                }
                result.onSuccess {
                    isSuccess(value = it)
                }
            }
        }
    }
    suspend fun getGuide(id: Int){
        repoGuide.getGuide(id).collect { result ->
            result.onSuccess {
                it.data?.let { guide -> getGuideSuccess(guide) }
            }
        }
    }
    private fun setLoading(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isLoading = value)
        }
    }

    private fun setError(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isError = value)
        }
    }

    private fun setEmpty(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isEmpty = value)
        }
    }
    private fun isSuccess(value : POIResponse){
        _state.update {
            it.copy(DetailResult = value)
        }
    }

    private fun getGuideSuccess(value:Guide){
        _state.update {
            it.copy(GuideResult = value)
        }
    }
}