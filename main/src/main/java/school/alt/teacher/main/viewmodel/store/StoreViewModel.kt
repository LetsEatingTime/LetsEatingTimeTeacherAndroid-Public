package school.alt.teacher.main.viewmodel.store

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import school.alt.teacher.main.viewmodel.home.NetworkResultState
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.ProductDto
import school.alt.teacher.network.store.StoreRepository
import school.alt.teacher.network.teacher.TeacherRepository
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val teacherRepository: TeacherRepository
) : ViewModel() {


    private val _isEdit = mutableStateOf(false)

    private val _produces = mutableStateOf<List<ProductDto>?>(null)
    private val _product = mutableStateOf<ProductDto?>(null)

    private var _productionState = MutableStateFlow(NetworkResultState())
    val productionState: SharedFlow<NetworkResultState> = _productionState

    private var _productList = MutableStateFlow<ProductUiState>(ProductUiState.Success(emptyList()))
    val productList: StateFlow<ProductUiState> = _productList

    private var _productImageList = MutableStateFlow<ProductImageUiState>(
        ProductImageUiState.Success(
            emptyMap()
        )
    )
    val productImageList: StateFlow<ProductImageUiState> = _productImageList


    val isEdit = _isEdit

    val products: State<List<ProductDto>?> = _produces
    val product: State<ProductDto?> = _product

    fun initData() {
        getLiveAllProduct()
        _product.value = null
    }

    fun setIsEdit(isEdit: Boolean) {
        _isEdit.value = isEdit
    }

    fun setData(product: ProductDto) {
        _product.value = product
    }

    init {
        getLiveAllProduct()
    }


    fun getLiveAllProduct() = viewModelScope.launch {
        kotlin.runCatching {
            storeRepository.getProductAll()
        }.onSuccess { profileFlow ->
            profileFlow.onEach {
                _productionState.emit(NetworkResultState("", true))
                _productList.value = ProductUiState.Success(it.data)
                _productList.emit(ProductUiState.Success(it.data))
                getProductsImages(it.data)
            }.launchIn(viewModelScope)
        }.onFailure { e ->
            e.printStackTrace()
            _productionState.emit(NetworkResultState(e.message.toString(), false))
        }
    }

    private fun getProductsImages(productsList: List<ProductDto>) = viewModelScope.launch {
        val imageMap = mutableMapOf<Int, ImageResponse>()
        productsList.forEach { productData ->
            val productIdx = productData.image
            if (productIdx != null) {
                kotlin.runCatching {
                    storeRepository.getProductImage(productIdx)
                }.onSuccess {
                    it.onEach { result ->
                        imageMap[productIdx] = result.data
                        _productImageList.emit(ProductImageUiState.Success(imageMap))
                    }.launchIn(viewModelScope)
                }.onFailure { e ->
                    e.printStackTrace()
                    _productImageList.emit(ProductImageUiState.Error(e))
                    _productionState.emit(NetworkResultState(e.message.toString(), false))
                }
            }
        }
    }

    fun getProduct(onAction: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeRepository.getProductInfo(product.value!!.idx!!)
            }.onSuccess { data ->
                _product.value = data.data
                onAction()
            }.onFailure { e ->
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        val errorBody = e.response.body
                        Log.e("Store", "Error body: $errorBody")
                    }
                }
            }
        }
    }

    fun getProductAllData(result: (Boolean) -> Unit) {
    }

    fun uploadImage(id: String, image: MultipartBody.Part) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            teacherRepository.uploadPhoto(
                image = image,
                id = id.toRequestBody("text/plain".toMediaTypeOrNull()),
                table = "product".toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }.onSuccess {

        }.onFailure { e->
            e.printStackTrace()
        }
    }


    fun createStoreData(productDto: ProductDto, result: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeRepository.createProduct(productDto)
            }.onSuccess { data ->
                result(true)
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("Store", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Store", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Store", "Error body: $errorBody")
                    }
                }
            }
        }
    }

    fun modifyStoreData(productDto: ProductDto, result: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeRepository.modifyProduct(
                    productId = productDto.idx!!,
                    body = productDto
                )
            }.onSuccess { data ->
                result(true)
            }.onFailure { e ->
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Store", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Store", "Error body: $errorBody")
                        result(false)
                    }
                }
            }
        }
    }

    fun getProductData(result: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeRepository.getProductInfo(id = _product.value!!.idx!!)
            }.onSuccess { data ->
                _product.value = data.data
                result(true)
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("Store", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Store", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Store", "Error body: $errorBody")
                    }
                }
            }
        }
    }

    fun deleteProductData(result: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeRepository.deleteProduct(id = _product.value!!.idx!!)
            }.onSuccess { data ->
                result(true)
            }.onFailure { e ->
                e.printStackTrace()
                Log.e("Store", "Error message: ${e.message}")
                withContext(Dispatchers.Main) {
                    if (e is HttpException) {
                        Log.e("Store", "HttpException occurred")
                        val errorBody = e.response.body
                        Log.e("Store", "Error body: $errorBody")
                    }
                }
            }
        }
    }
}

sealed class ProductUiState {
    data class Success(val products: List<ProductDto>) : ProductUiState()
    data class Error(val exception: Throwable) : ProductUiState()
}

sealed class ProductImageUiState {
    data class Success(val products: Map<Int, ImageResponse>) : ProductImageUiState()
    data class Error(val exception: Throwable) : ProductImageUiState()
}

