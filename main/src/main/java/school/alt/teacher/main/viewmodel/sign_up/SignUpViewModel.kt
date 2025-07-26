package school.alt.teacher.main.viewmodel.sign_up

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import school.alt.teacher.network.account.AccountRepository
import school.alt.teacher.network.dto.SignUpRequestDto
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _id = mutableStateOf("")
    private val _password = mutableStateOf("")
    private val _name = mutableStateOf("")
    val id: State<String> = _id
    val password: State<String> = _password
    val name: State<String> = _name

    fun setId(inputId: String) {
        _id.value = inputId
    }

    fun setPassword(inputPassword: String) {
        _password.value = inputPassword
    }

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage

    fun initToastMessage() {
        _toastMessage.value = ""
    }


    fun setName(inputName: String) {
        _name.value = inputName
    }

    fun initValues() {
        _id.value = ""
        _password.value = ""
        _name.value = ""
    }

    fun signUp(userData: SignUpRequestDto, onAction: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                accountRepository.signup(userData)
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("Login", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Login", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Login", "Error body: $errorBody")
                    }
                    _toastMessage.value = "회원가입에 실패했습니다."
                }


            }
        }

    }
}