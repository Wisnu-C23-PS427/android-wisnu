package space.mrandika.wisnu.ui.city.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.city.City
import space.mrandika.wisnu.repository.CityRepository
import javax.inject.Inject
@HiltViewModel
class CityViewModel @Inject constructor(private val repo : CityRepository): ViewModel() {
    private var _state = MutableStateFlow(CityUiState())
    val state: StateFlow<CityUiState> = _state

    fun getCity(idCity : Int){
        viewModelScope.launch {
            isError(false)
            isLoading(true)

            repo.getCity(idCity).collect{ result ->
                isLoading(false)

                result.onFailure {
                    isError(true)
                }

                result.onSuccess {response->
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