package space.mrandika.wisnu.ui.itinerary

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.city.ItineraryItem
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.transaction.OrderResponse
import space.mrandika.wisnu.model.transaction.POIGuideOrder
import space.mrandika.wisnu.model.transaction.POITicketOrder
import space.mrandika.wisnu.repository.CityRepository
import space.mrandika.wisnu.repository.POIRepository
import space.mrandika.wisnu.repository.TransactionRepository
import javax.inject.Inject
@HiltViewModel
class ItineraryViewModel @Inject constructor(
    private val repo : POIRepository,
    private val transactionRepository: TransactionRepository
):ViewModel(){
    private val _state = MutableStateFlow(ItineraryUiState())
    val state : StateFlow<ItineraryUiState> = _state

    suspend fun getRecommendation(cityId: Int) {
        isLoading(true)
        isError(false)

        repo.getPOIsByCity(cityId).collect {
            isLoading(false)

            it.onFailure {
                isError(true)
            }

            it.onSuccess { result ->
                success(result.data)
            }
        }
    }

    suspend fun createTransaction(callback: (OrderResponse) -> Unit) {
        isLoading(true)
        isError(false)

        transactionRepository.createTransaction(_state.value.tickets, _state.value.guide).collect {
            isLoading(false)

            it.onFailure {
                isError(true)
            }

            it.onSuccess { result ->
                Log.d("ItineraryViewModel", result.toString())
                callback(result)
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

    private fun success(value: List<ItineraryItem>){
        _state.update {
            it.copy(itineraries = value)
        }
    }

    fun setCityId(id: Int) {
        _state.update {
            it.copy(cityId = id)
        }
    }

    fun setTicket(tickets : List<POITicketOrder>){
        _state.update {
            it.copy(tickets = tickets)
        }
    }

    fun setGuide(guide: POIGuideOrder?){
        _state.update {
            it.copy(guide = guide)
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