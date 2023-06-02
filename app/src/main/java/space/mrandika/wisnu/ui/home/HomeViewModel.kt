package space.mrandika.wisnu.ui.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.model.poi.POIsResponse
import space.mrandika.wisnu.repository.EventRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val eventRepo:EventRepository, private val recommendationRepo: POIRepository) :ViewModel(){
    private var _state = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state

    suspend fun getRecommendation(){
        isLoading(true)
        isError(false)
        recommendationRepo.getRecommendedPOIs().collect{ result ->
            isLoading(false)
            result.onSuccess {
                getRecommendedSuccess(it.data)
            }
            result.onFailure {
                isError(true)
            }
        }
    }
    suspend fun getEvent(){
        isLoading(true)
        isError(false)
        eventRepo.getEvents().collect{ result ->
            isLoading(false)

            result.onSuccess {
                getEventsSuccess(it.data)
            }
            result.onFailure {
                isError(true)
            }
        }
    }
    private fun isLoading(value : Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun isError(value : Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun getRecommendedSuccess(value : List<POI>){
        _state.update { result ->
            result.copy(recommendation = value)
        }
    }
    fun getEventsSuccess(value: List<Event>){
        _state.update { result ->
            result.copy(events = value)
        }
    }
}