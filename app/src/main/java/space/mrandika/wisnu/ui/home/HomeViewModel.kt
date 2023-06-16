package space.mrandika.wisnu.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.model.event.Event
import space.mrandika.wisnu.model.poi.POI
import space.mrandika.wisnu.repository.EventRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepo: EventRepository,
    private val poiRepo: POIRepository
) : ViewModel() {
    private var _state = MutableStateFlow(HomeUiState())
    val state : StateFlow<HomeUiState> = _state

    fun getCategories(){
        viewModelScope.launch {
            isLoading(true)
            isError(false)

            poiRepo.getCategories().collect{ result ->
                isLoading(false)
                result.onSuccess {
                    setCategories(it.data)
                }
                result.onFailure {
                    isError(true)
                }
            }
        }
    }

    fun getRecommendation(){
        Log.d("HomeViewModel", "getting recommendation...")
        viewModelScope.launch {
            isLoading(true)
            isError(false)
            poiRepo.getRecommendedPOIs(preview = true).collect{ result ->
                isLoading(false)
                result.onSuccess {
                    setRecommendations(it.data)
                }
                result.onFailure {
                    isError(true)
                }
            }
        }
    }
    fun getEvent(){
        viewModelScope.launch {
            isLoading(true)
            isError(false)
            eventRepo.getEvents(preview = true).collect{ result ->
                isLoading(false)

                result.onSuccess {
                    setEvents(it.data)
                }
                result.onFailure {
                    isError(true)
                }
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

    private fun setCategories(value: List<Category>){
        _state.update { result ->
            result.copy(categories = value)
        }
    }

    private fun setRecommendations(value : List<POI>){
        _state.update { result ->
            result.copy(recommendations = value)
        }
    }
    fun setEvents(value: List<Event>){
        _state.update { result ->
            result.copy(events = value)
        }
    }
}