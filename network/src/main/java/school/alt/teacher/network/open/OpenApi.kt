package school.alt.teacher.network.open

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import school.alt.teacher.network.dto.MealResponse
import school.alt.teacher.network.dto.ResponseDto

interface OpenApi {
    @Headers("Auth: false")
    @GET("openapi/meal")
    suspend fun getMeal(@Query("date") date: String): ResponseDto<MealResponse>

    @Headers("Auth: false")
    @GET("/api/meal/analysis/student/{userId}")
    suspend fun getStudentMeal(@Path("userId") userId: Int): ResponseDto<List<String?>>

    @Headers("Auth: false")
    @GET("/api/meal/statistics/leftovers")
    suspend fun getLeftoversData(): ResponseDto<List<String?>>

    @Headers("Auth: false")
    @GET("/api/meal/statistics/date")
    suspend fun getStatisticsData(@Path("userId") userId: Int): ResponseDto<List<String>>
}