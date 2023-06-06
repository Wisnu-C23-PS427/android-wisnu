package space.mrandika.wisnu.ui.itinerary

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ItineraryViewModel @Inject constructor():ViewModel(){
    private val _state = MutableStateFlow(itineraryUiState())
    val state : StateFlow<itineraryUiState> = _state

    fun setCity(cityName : String){
        _state.update {
            it.copy(city = cityName)
        }
    }
    fun setAdult(adult : Int){
        _state.update {
            it.copy(adult = adult)
        }
    }
    fun setChild(adult : Int){
        _state.update {
            it.copy(child = adult)
        }
    }
}