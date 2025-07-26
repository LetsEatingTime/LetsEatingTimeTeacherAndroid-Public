package school.alt.teacher.main.viewmodel.change_password

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
import school.alt.teacher.network.account.AccountRepositoryImpl
import school.alt.teacher.network.dto.PasswordChangeRequest
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val accountRepositoryImpl: AccountRepositoryImpl
) : ViewModel() {
    private val _emile = mutableStateOf("")
    val emile: State<String> = _emile
    private val _newPassword = mutableStateOf("")
    val newPassword: State<String> = _newPassword

    private val _toastMessage = mutableStateOf("")
    val toastMessage: State<String> = _toastMessage

    fun initToastMessage() {
        _toastMessage.value = ""
    }


    fun setId(inputId: String) {
        _emile.value = inputId
    }

    fun setNewPassword(inputNewPassword: String) {
        _newPassword.value = inputNewPassword
    }

    fun initValues() {
        _emile.value = ""
        _newPassword.value = ""
    }

    fun changePassword(inPutData: PasswordChangeRequest, onAction: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                accountRepositoryImpl.changePassword(inPutData)
            }.onSuccess { result ->
                _toastMessage.value = "비밀번호 변경 성공"
                withContext(Dispatchers.Main) {
                    onAction()
                }
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("changePassword", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Open", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Open", "Error body: $errorBody")
                    }
                }
                _toastMessage.value = "비밀번호 변경 실패"
            }

        }
    }

}