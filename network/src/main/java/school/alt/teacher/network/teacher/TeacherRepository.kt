package school.alt.teacher.network.teacher

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import school.alt.teacher.network.dto.EditUserRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.UserDto
import school.alt.teacher.network.dto.UserInfoDto

interface TeacherRepository {
    suspend fun uploadPhoto(image: MultipartBody.Part, table: RequestBody, id: RequestBody)
    suspend fun editStudent(body: EditUserRequest): ResponseDto<String>
    suspend fun getUser(id: Int?): Flow<ResponseDto<UserDto>>
    suspend fun getUsers(): Flow<ResponseDto<List<UserDto>>>
    suspend fun deleteUser(id: String): ResponseDto<String>
    suspend fun appreve(page: Int, size: Int): Flow<ResponseDto<List<UserInfoDto>>>
    suspend fun appreveUser(id: String): ResponseDto<String>
}