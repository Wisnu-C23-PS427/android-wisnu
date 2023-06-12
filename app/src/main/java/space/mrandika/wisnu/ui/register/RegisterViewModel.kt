package space.mrandika.wisnu.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.model.category.Category
import space.mrandika.wisnu.repository.AuthRepository
import space.mrandika.wisnu.repository.POIRepository
import javax.inject.Inject
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val poiRepo: POIRepository
) : ViewModel() {
    private var _state = MutableStateFlow(RegisterUiState())
    val state : StateFlow<RegisterUiState> = _state

    fun registerUsers(name:String, email:String, phoneNumber: String, password : String, interest : List<String>){
        viewModelScope.launch {
            isLoading(true)

            repo.register(name,email,phoneNumber,password,interest).collect { result ->
                isLoading(false)

                result.onFailure {
                    isError(true)
                }
                result.onSuccess {
                    registerSuccess(it)
                }
            }
        }
    }

    suspend fun getCategories() {
        isLoading(true)
        isError(false)

        poiRepo.getCategories().collect{ result ->
            isLoading(false)

            result.onSuccess {
                setInterestData(it.data)
            }

            result.onFailure {
                isError(true)
            }
        }
    }

    fun updateName(value: String) {
        _state.update { currentState ->
            currentState.copy(name = value)
        }
    }

    fun updateEmail(value: String) {
        _state.update { currentState ->
            currentState.copy(email = value)
        }
    }

    fun updatePhoneNumber(value: String) {
        _state.update { currentState ->
            currentState.copy(phoneNumber = value)
        }
    }

    fun updatePassword(value: String) {
        _state.update { currentState ->
            currentState.copy(password = value)
        }
    }

    fun updateInteresting(value: MutableList<String>){
        _state.update {
            it.copy(interest = value)
        }
    }

    private fun setInterestData(value: List<Category>){
        _state.update {
            it.copy(interestData = value)
        }
    }

    private fun isLoading (value : Boolean){
        _state.update {
            it.copy(isLoading = value)
        }
    }
    private fun isError (value : Boolean){
        _state.update {
            it.copy(isError = value)
        }
    }
    private fun registerSuccess(value : RegisterResponse){
        _state.update {
            it.copy(result = value)
        }
    }
}