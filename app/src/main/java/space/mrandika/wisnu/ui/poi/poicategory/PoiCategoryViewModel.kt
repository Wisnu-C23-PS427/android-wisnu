package space.mrandika.wisnu.ui.poi.poicategory

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.poi.POIResponse
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject
@HiltViewModel
class PoiCategoryViewModel @Inject constructor(private val repo : POIRepository): ViewModel() {
    private val _state = MutableStateFlow(CategoryUiState())
    val state : StateFlow<CategoryUiState> = _state

    suspend fun getPoiCategory(category  : String){
        isLoading(true)
        isError(false)
        repo.getPOIsByCategory(category).collect{result ->
            isLoading(false)
            result.onFailure {
                isError(true)
            }
            result.onSuccess {
                success(it.data)
            }
        }
    }
    private fun isLoading(value:Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun isError(value:Boolean){
        _state.update {
            it.copy(isError = value)
        }
    }
    private fun success(value: List<POI>){
        _state.update {
            it.copy(CategoryResult = value)
        }
    }
}