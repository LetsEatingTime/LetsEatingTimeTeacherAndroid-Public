package school.alt.teacher.network.store

import kotlinx.coroutines.flow.Flow
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.ProductDto
import school.alt.teacher.network.dto.ResponseDto

interface StoreRepository {
    suspend fun getProductAll(): Flow<ResponseDto<List<ProductDto>>>
    suspend fun getProductInfo(id: Int): ResponseDto<ProductDto>
    suspend fun createProduct(body: ProductDto): ResponseDto<String>
    suspend fun modifyProduct(productId: Int, body: ProductDto): ResponseDto<String>
    suspend fun deleteProduct(id: Int): ResponseDto<String>
    suspend fun getProductImage(idx: Int): Flow<ResponseDto<ImageResponse>>
}