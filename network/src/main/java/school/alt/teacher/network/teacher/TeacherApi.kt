package school.alt.teacher.network.teacher

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import school.alt.teacher.network.dto.EditUserRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto

interface TeacherApi {
    @Multipart
    @POST("teacher/id-photo/upload")
    suspend fun uploadPhoto(
        @Part image: MultipartBody.Part,
        @Part("table") table: RequestBody, // table은 user, product 값만을 넣을 수 있다.
        @Part("id") id: RequestBody,
    )

    @POST("teacher/edit/student")
    suspend fun editStudent(@Body body: EditUserRequest): ResponseDto<String>

    @GET("teacher/approve")
    suspend fun appreve(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ResponseDto<List<UserInfoDto>>

    @POST("teacher/approve")
    suspend fun appreveUser(@Query("id") id: String): ResponseDto<String>

    @GET("teacher/get/user")
    suspend fun getUser(
        @Query("id") id: Int?
    ): ResponseDto<UserDto>

    @GET("teacher/get/user?id")
    suspend fun getUsers(
    ): ResponseDto<List<UserDto>>

    @DELETE("teacher/delete/user")
    suspend fun deleteUser(@Query("id") id: String): ResponseDto<String>
}