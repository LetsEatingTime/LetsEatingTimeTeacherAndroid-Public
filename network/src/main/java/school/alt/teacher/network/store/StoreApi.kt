package school.alt.teacher.network.store

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.ProductDto
import school.alt.teacher.network.dto.ResponseDto

interface StoreApi {
    @Headers("Auth: false")
    @GET("product/all")
    suspend fun getProductAll(): ResponseDto<List<ProductDto>>

    @Headers("Auth: false")
    @GET("product/{id}")
    suspend fun getProductInfo(@Path("id") id: Int): ResponseDto<ProductDto>

    @Headers("Auth: false")
    @POST("product/create")
    suspend fun createProduct(@Body body: ProductDto): ResponseDto<String>

    @Headers("Auth: false")
    @PUT("product/update/{productId}")
    suspend fun modifyProduct(
        @Path("productId") productId: Int,
        @Body body: ProductDto
    ): ResponseDto<String>

    @Headers("Auth: false")
    @DELETE("product/delete/{productId}")
    suspend fun deleteProduct(@Path("id") id: Int): ResponseDto<String>

    @Headers("Auth: false")
    @GET("/api/file/get/{idx}")
    suspend fun getProductImage(@Path("idx") idx: Int): ResponseDto<ImageResponse>


}