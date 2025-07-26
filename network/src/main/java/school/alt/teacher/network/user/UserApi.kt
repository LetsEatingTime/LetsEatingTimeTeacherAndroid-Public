package school.alt.teacher.network.user

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.UserDto

interface UserApi {
    @POST("user/withdraw")
    suspend fun withdraw(): ResponseDto<String>

    @GET("user/profile")
    suspend fun getProfile(): ResponseDto<UserDto>

    @GET("user/image/{idx}")
    suspend fun getImage(@Path("idx") idx: Int): ImageResponse

    @GET("api/meal/analysis/student/{userId}")
    suspend fun getStudentMealAnalysis(@Path("userId") idx: Int): String
}