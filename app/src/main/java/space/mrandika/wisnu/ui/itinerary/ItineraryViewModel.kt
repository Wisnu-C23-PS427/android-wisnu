package space.mrandika.wisnu.ui.itinerary

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.repository.CityRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject
@HiltViewModel
class ItineraryViewModel @Inject constructor(private val repo : POIRepository):ViewModel(){
    private val _state = MutableStateFlow(ItineraryUiState())
    val state : StateFlow<ItineraryUiState> = _state

    suspend fun getRecommendation() {
        isLoading(true)
        isError(false)
        repo.getPOIsByCity(1).collect {
            isLoading(false)
            it.onFailure {
                isError(true)
            }
            it.onSuccess { result ->
                success(result.data)
            }
        }
    }

    private fun setDataTransaction(ticket : Int,total : Int, guide : Int){
        _state.update {
            it.copy(ticketTotal = ticket, totalCost = total, guide = guide)
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
    private fun success(value : List<POI>?){
        _state.update {
            it.copy(PoiData = value)
        }
    }
    fun setTotalTicket(totalTicket : Int){
        _state.update {
            it.copy(ticketTotal = totalTicket)
        }
    }
    fun setTotalGuide(guidePrice:Int){
        _state.update {
            it.copy(guide = guidePrice)
        }
    }
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