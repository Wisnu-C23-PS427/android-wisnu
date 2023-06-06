package space.mrandika.wisnu.ui.itinerary.cityDetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.repository.CityRepository
import javax.inject.Inject
@HiltViewModel
class CityViewModel @Inject constructor(private val repo : CityRepository): ViewModel() {
    private var _state = MutableStateFlow(CityUiState())
    val state: StateFlow<CityUiState> = _state

    suspend fun getCity(idCity : Int){
        isLoading(true)
        isError(false)
        isEmpty(false)
        repo.getCity(idCity).collect{ result ->
            isLoading(false)

            result.onFailure {
                isError(true)
            }

            result.onSuccess {response->
                if (response.data == null){
                    isEmpty(true)
                }else{
                    success(response.data)
                }
            }
        }
    }

    private fun isLoading (value: Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun isEmpty (value: Boolean){
        _state.update {
            it.copy(isEmpty = value)
        }
    }
    private fun isError (value: Boolean){
        _state.update {
            it.copy(isError = value)
        }
    }
    private fun success (value: City?){
        _state.update {
            it.copy(CityResult = value)
        }
    }
}