package space.mrandika.wisnu.ui.register

import space.mrandika.wisnu.model.auth.RegisterResponse
import space.mrandika.wisnu.model.category.Category

data class RegisterUiState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val name : String = "",
    val email : String = "",
    val phoneNumber : String = "",
    val password : String = "",
    val interestData: List<Category> = emptyList(),
    val interest : MutableList<String> = mutableListOf(),
    val result : RegisterResponse? = null
)