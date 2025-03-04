package school.alt.teacher.network.user

import kotlinx.coroutines.flow.Flow
import school.alt.teacher.network.dto.ImageResponse
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.UserDto

interface UserRepository {
    suspend fun withdraw(): ResponseDto<String>
    suspend fun getProfile(): Flow<ResponseDto<UserDto>>
    suspend fun getUserImage(idx: Int): Flow<ImageResponse>
    suspend fun getStudentMealAnalysis(idx: Int): Flow<String>
}