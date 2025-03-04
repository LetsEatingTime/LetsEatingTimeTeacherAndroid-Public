package school.alt.teacher.main.viewmodel.scan

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
) : ViewModel() {

    private val _isEdit = mutableStateOf(false)
    private val _imageUri = mutableStateOf<Uri>("".toUri())

    private val _student = mutableIntStateOf(-1)

    val isEdit: State<Boolean> = _isEdit
    val imageUri: State<Uri> = _imageUri

    fun setIsEdit(inputValue: Boolean) {
        _isEdit.value = inputValue
    }

    fun setUri(inputValue: Uri) {
        _imageUri.value = inputValue
    }

    fun initValues() {
        _student.intValue = -1
    }

    fun getPrizeInfo(): String {
        return ""
    }

}