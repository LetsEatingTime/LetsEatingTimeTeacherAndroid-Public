package school.alt.teacher.main.ui.screen.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import school.alt.teacher.main.ui.compose.button.TextButton2
import school.alt.teacher.main.ui.compose.bar.NavBar
import school.alt.teacher.main.ui.compose.textfield.TextFieldBorder
import school.alt.teacher.main.ui.foundation.IcAddPhoto
import school.alt.teacher.main.ui.foundation.IcBack
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.line2
import school.alt.teacher.main.ui.theme.line3
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.viewmodel.store.ProductImageUiState
import school.alt.teacher.main.viewmodel.store.StoreViewModel
import school.alt.teacher.network.dto.ProductDto
import java.io.File
import java.io.FileOutputStream
import java.time.LocalTime

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel,

    onMoveScreen: () -> Unit,
) {
    val productionImageList by storeViewModel.productImageList.collectAsState()
    val isImageSelect = remember {
        mutableStateOf(true)
    }

    val isImageChanged = remember {
        mutableStateOf(false)
    }

    val productIdx = remember {
        val idx = storeViewModel.product.value?.idx
        mutableStateOf(idx)
    }

    var prizeNameText by remember {
        mutableStateOf(storeViewModel.product.value?.productName ?: "")
    }

    var prizeCostText by remember {
        mutableStateOf(storeViewModel.product.value?.price.toString())
    }

    var prizeStockText by remember {
        mutableStateOf(storeViewModel.product.value?.stock.toString())
    }

    val context = LocalContext.current


    val imageData = remember {
        mutableStateOf(
            when (productionImageList) {
                is ProductImageUiState.Success -> {
                    (productionImageList as ProductImageUiState.Success).products[storeViewModel.product.value?.image]?.fileName
                        ?: ""
                }

                is ProductImageUiState.Error -> {
                    ""
                }
            }
        )
    }

    if (storeViewModel.isEdit.value) {
        storeViewModel.initData()
    }

    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            storeViewModel.getProduct() {
                prizeNameText = storeViewModel.product.value?.productName ?: ""
                prizeCostText = storeViewModel.product.value?.price.toString()
            }
        }

        onDispose {
            job.cancel()
        }
    }

    val getImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                isImageSelect.value = true
                imageData.value = uri.toString()
                isImageChanged.value = true
            }
        }
    )

    Box(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize()
    ) {
        Column {
            NavBar(
                modifier = Modifier.fillMaxWidth(),
                padding = PaddingValues(3.dp),
                startContent = {
                    IconButton(modifier = it, onClick = {
                        onMoveScreen()
                    }) {
                        IcBack(
                            contentDescription = "",
                            tint = line3
                        )
                    }
                },
                isClickable = false
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { getImage.launch("image/*") }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .background(color = line2)
                        .height(230.dp)
                        .fillMaxWidth(),
                    model = imageData.value.ifEmpty {
                        ""
                    },
                    contentDescription = ""
                )
                if (imageData.value.isBlank()) {
                    IcAddPhoto(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(18.dp),
                        contentDescription = "",
                        size = 40.dp,
                        tint = line1
                    )
                }
            }
            TextFieldBorder(
                text = prizeNameText,
                borderTitle = "이벤트 이름",
                label = "이벤트를을 입력 해 주세요",
                singleLine = true,
                onValueChange = { prizeNameText = it }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = prizeCostText,
                borderTitle = "포인트",
                label = "포인트를 입력 해 주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                onValueChange = { }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextFieldBorder(
                text = prizeStockText,
                borderTitle = "수량",
                label = "수량을 입력 해 주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                singleLine = true,
                onValueChange = { prizeStockText = it }
            )
            Spacer(modifier = Modifier.padding(top = 50.dp))

        }
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth()
                .padding(bottom = 50.dp)
                .align(Alignment.BottomCenter)
        ) {
            TextButton2(
                modifier = Modifier.fillMaxWidth(),
                text = if (storeViewModel.isEdit.value) {
                    "상품변경하기"
                } else {
                    "상품추가하기"
                },
                enabled = prizeCostText.isNotBlank()
                        && prizeStockText.isNotBlank()
                        && prizeNameText.isNotBlank()
                        && prizeCostText.toInt() >= 0
                        && prizeStockText.toInt() >= 0,
                backgroundColor = main2
            ) {
                if (storeViewModel.isEdit.value) {
                    storeViewModel.modifyStoreData(
                        ProductDto(
                            idx = productIdx.value!!,
                            productName = prizeNameText,
                            price = prizeCostText.toInt(),
                            stock = prizeStockText.toInt(),
                            grantTime = storeViewModel.product.value?.grantTime ?: "",
                            image = null
                        )
                    ) {
                    }
                    if (isImageChanged.value) {
                        val image = prepareFilePart(imageData.value.toUri(), context)
                        if (image != null) {
                            storeViewModel.uploadImage(
                                image = image,
                                id = prizeNameText
                            )
                        }
                    }
                } else {
                    storeViewModel.createStoreData(
                        productDto = ProductDto(
                            grantTime = LocalTime.now().toString(),
                            image = null,
                            price = prizeCostText.toInt(),
                            productName = prizeNameText,
                            stock = 0,
                            idx = null
                        )
                    ) {
                    }
                    if (isImageChanged.value) {
                        val image = prepareFilePart(imageData.value.toUri(), context)
                        if (image != null) {
                            storeViewModel.uploadImage(
                                image = image,
                                id = prizeNameText
                            )
                        }
                    }
                }
                onMoveScreen()
                storeViewModel.getProductAllData {}
                storeViewModel.initData()
            }
        }
    }
}

private fun prepareFilePart(uri: Uri, context: Context): MultipartBody.Part? {
    val file = getFileFromUri(uri = uri, context = context)
    return file?.let {
        val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", it.name, requestFile)
    }
}

private fun getFileFromUri(uri: Uri, context: Context): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}