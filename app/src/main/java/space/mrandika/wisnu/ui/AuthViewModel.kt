package space.mrandika.wisnu.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import space.mrandika.wisnu.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    fun getAccessToken(): Flow<String?> {
        return repo.getAccessToken()
    }
}