package school.alt.teacher.network.store

import kotlinx.coroutines.flow.flow
import school.alt.teacher.network.dto.ProductDto
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.store.StoreApi
import school.alt.teacher.network.store.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi
) : StoreRepository {
    override suspend fun getProductAll() = flow {
        emit(storeApi.getProductAll())
    }

    override suspend fun getProductInfo(id: Int): ResponseDto<ProductDto> {
        return storeApi.getProductInfo(id = id)
    }

    override suspend fun createProduct(body: ProductDto): ResponseDto<String> {
        return storeApi.createProduct(body = body)
    }

    override suspend fun modifyProduct(productId: Int, body: ProductDto): ResponseDto<String> {
        return storeApi.modifyProduct(productId = productId, body = body)
    }

    override suspend fun deleteProduct(id: Int): ResponseDto<String> {
        return storeApi.deleteProduct(id = id)
    }

    override suspend fun getProductImage(idx: Int) = flow {
        emit(storeApi.getProductImage(idx))
    }
}