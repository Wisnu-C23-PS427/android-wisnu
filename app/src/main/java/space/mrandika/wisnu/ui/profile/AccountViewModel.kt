package space.mrandika.wisnu.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import space.mrandika.wisnu.model.account.Account
import space.mrandika.wisnu.repository.AccountRepository
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repo: AccountRepository
): ViewModel() {
    private val _state = MutableStateFlow(AccountUiState())
    val state: StateFlow<AccountUiState> = _state

    suspend fun getAccount() {
        Log.d("AccountViewModel", "Starting to get account...")
        setError(false)
        setLoading(true)

        repo.getAccount().collect { result ->
            setLoading(false)

            result.onSuccess { response ->
                response.data?.account?.let { account ->
                    setData(account)
                }

                Log.d("AccountViewModel", response.toString())
            }

            result.onFailure {
                setError(true)
            }
        }
    }

    private fun setLoading(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isLoading = value)
        }
    }

    private fun setError(value: Boolean) {
        _state.update { currentState ->
            currentState.copy(isError = value)
        }
    }

    private fun setData(value: Account) {
        _state.update { currentState ->
            currentState.copy(account = value)
        }
    }
}