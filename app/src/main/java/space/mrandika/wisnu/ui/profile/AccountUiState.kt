package space.mrandika.wisnu.ui.profile

import space.mrandika.wisnu.model.account.Account

data class AccountUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val account: Account? = null
)