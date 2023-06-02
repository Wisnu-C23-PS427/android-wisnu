package space.mrandika.wisnu.ui.register

import space.mrandika.wisnu.model.auth.RegisterResponse

data class RegisterUiState (
    val isLoading : Boolean = false ,
    val isError : Boolean = false,
    val name : String = "",
    val email : String = "",
    val phoneNumber : String = "",
    val password : String = "",
    val interest : List<String> = emptyList(),
    val result : RegisterResponse? = null
        )