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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import school.alt.teacher.main.ui.compose.button.Button2
import school.alt.teacher.main.ui.compose.navBar.NavBar
import school.alt.teacher.main.ui.compose.textfild.TextBorder
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

    onMoveScreen: () -> Unit
) {
    val productionImageList by storeViewModel.productImageList.collectAsState()
    val isImageSelect = remember {
        mutableStateOf(true)
    }

    val isImageChanged = remember {
        mutableStateOf(false)
    }

    val productIdx = remember{
        val idx = storeViewModel.product.value?.idx
        Log.d("test", "storeViewModel.product.value?.idx : ${idx} if")
        mutableStateOf(idx)
    }

    val prizeNameText = remember {
        mutableStateOf(storeViewModel.product.value?.productName ?: "")
    }

    val prizeCostText = remember {
        mutableStateOf(storeViewModel.product.value?.price.toString())
    }

    val prizeStockText = remember {
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
                prizeNameText.value = storeViewModel.product.value?.productName ?: ""
                prizeCostText.value = storeViewModel.product.value?.price.toString()
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
                    .clickable(
//                        enabled = !storeViewModel.isEdit.value
                    ) {
                        getImage.launch("image/*")
                    }) {
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
            TextBorder(
                text = prizeNameText,
                titleText = "이벤트 이름",
                label = "이벤트를을 입력 해 주세요",
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextBorder(
                text = prizeCostText,
                titleText = "포인트",
                label = "포인트를 입력 해 주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextBorder(
                text = prizeStockText,
                titleText = "수량",
                label = "수량을 입력 해 주세요",
                keyboardType = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                singleLine = true
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
            Button2(
                modifier = Modifier.fillMaxWidth(),
                text = if (storeViewModel.isEdit.value) {
                    "상품변경하기"
                } else {
                    "상품추가하기"
                },
                enabled = prizeCostText.value.isNotBlank()
                        && prizeStockText.value.isNotBlank()
                        && prizeNameText.value.isNotBlank()
                        && prizeCostText.value.toInt() >= 0
                        && prizeStockText.value.toInt() >= 0,
                backgroundColor = main2
            ) {
                if (storeViewModel.isEdit.value) {
                    Log.d("test", "storeViewModel.product.value?.idx : ${productIdx.value} if")

                    storeViewModel.modifyStoreData(
                        ProductDto(
                            idx = productIdx.value!!,
                            productName = prizeNameText.value,
                            price = prizeCostText.value.toInt(),
                            stock = prizeStockText.value.toInt(),
                            grantTime = storeViewModel.product.value?.grantTime ?: "",
                            image = null
                        )
                    ) {
                    }
                    if(isImageChanged.value){
                        Log.d("test", " in if")
                        val image = prepareFilePart(imageData.value.toUri(), context)
                        Log.d("test", " in if null")
                        if (image != null) {
                            storeViewModel.uploadImage(
                                image = image,
                                id = prizeNameText.value
                            )
                        }
                    }
                } else {
                    Log.d("test", " isImageChangedsafasdf : ${productIdx} if")
                    storeViewModel.createStoreData(
                        productDto = ProductDto(
                            grantTime = LocalTime.now().toString(),
                            image = null,
                            price = prizeCostText.value.toInt(),
                            productName = prizeNameText.value,
                            stock = 0,
                            idx = null
                        )
                    ) {
                    }
                    if(isImageChanged.value){
                        Log.d("test", " in if")
                        val image = prepareFilePart(imageData.value.toUri(), context)
                        Log.d("test", " in if null")
                        if (image != null) {
                            storeViewModel.uploadImage(
                                image = image,
                                id = prizeNameText.value
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

private fun prepareFilePart(uri: Uri, context : Context): MultipartBody.Part? {
    Log.d("test", "testsafsaf si ni ni nsdfsaf")
    val file = getFileFromUri(uri = uri, context = context)
    return file?.let {
        val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData("image", it.name, requestFile)
    }
}

private fun getFileFromUri(uri: Uri, context : Context): File? {
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
@Preview(showBackground = true)
@Composable
fun TestStoreScreen() {
//    StoreScreen(
//        modifier = Modifier.padding(horizontal = 18.dp),
//        storeViewModel = StoreViewModel()
//    ){}
}