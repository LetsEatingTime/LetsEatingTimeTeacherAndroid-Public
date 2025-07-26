package school.alt.teacher.main.viewmodel.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import school.alt.teacher.data.TokenManager
import school.alt.teacher.network.account.AccountRepository
import school.alt.teacher.network.dto.LoginRequest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _id = mutableStateOf("")
    val id: State<String> = _id
    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun setId(inputId: String) {
        _id.value = inputId
    }

    fun setPassword(inputPassword: String) {
        _password.value = inputPassword
    }

    private var _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Success(false))
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun login() = viewModelScope.launch {
        runCatching {
            accountRepository.login(
                loginRequest = LoginRequest(
                    id = _id.value,
                    password = _password.value
                )
            ).first()
        }.onSuccess { result ->
            tokenManager.saveAccessToken(result.data.accessToken)
            tokenManager.saveRefreshToken(result.data.refreshToken)
            _loginUiState.emit(LoginUiState.Success(true))

        }.onFailure { e ->
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                if (e is HttpException) {
                    val code = e.code()
                    if (code != 500) {
                        val message = e.response()?.raw()?.message

                        _loginUiState.emit(
                            LoginUiState.Error(
                                exception = e, message = when (message) {
                                    "Account without" -> "존재하지 않는 계정 입니다."
                                    "Credentials failed." -> "아이디 또는 비밀번호가 틀렸습니다."
                                    else -> "알 수 없는 문제"
                                }
                            )
                        )
                    } else {
                        _loginUiState.emit(
                            LoginUiState.Error(exception = e, message = "서버에서 문제가 발생했습니다.")
                        )
                    }
                }
            }
        }
    }

    fun setAutoLogin(isAuto: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        tokenManager.saveAutoLoginStatus(isAuto)
    }

    fun getAutoLogin() = tokenManager.getAutologin()
    fun getAccessToken() = tokenManager.getAccessToken()
}

sealed class LoginUiState {
    data class Success(var isLogin: Boolean) : LoginUiState()
    data class Error(val exception: Throwable?, val message: String = "") : LoginUiState()
}