package space.mrandika.wisnu.ui.login

import space.mrandika.wisnu.model.auth.LoginResponse

data class LoginUiState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val LoginResult : LoginResponse? = null
        )