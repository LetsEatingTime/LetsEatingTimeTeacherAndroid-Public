package school.alt.teacher.network.open

import kotlinx.coroutines.flow.Flow
import school.alt.teacher.network.dto.MealResponse
import school.alt.teacher.network.dto.ResponseDto

interface OpenRepository {
    suspend fun getMeal(date: String): Flow<ResponseDto<MealResponse>>
    suspend fun getStudentMeal(userId: Int): Flow<ResponseDto<List<String?>>>
    suspend fun getLeftoversData(): Flow<ResponseDto<List<String?>>>
    suspend fun getStatisticsData(userId: Int): Flow<ResponseDto<List<String>>>
}