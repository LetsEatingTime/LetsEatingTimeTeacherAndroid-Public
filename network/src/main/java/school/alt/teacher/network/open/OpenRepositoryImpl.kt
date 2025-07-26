package school.alt.teacher.network.open

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import school.alt.teacher.network.dto.ResponseDto
import javax.inject.Inject

class OpenRepositoryImpl @Inject constructor(
    private val openApi: OpenApi
) : OpenRepository {
    override suspend fun getMeal(date: String) = flow {
        emit(openApi.getMeal(date))
    }

    override suspend fun getStudentMeal(userId: Int): Flow<ResponseDto<List<String?>>> = flow {
        emit(openApi.getStudentMeal(userId))
    }

    override suspend fun getLeftoversData(): Flow<ResponseDto<List<String?>>> = flow {
        emit(openApi.getLeftoversData())
    }

    override suspend fun getStatisticsData(userId: Int): Flow<ResponseDto<List<String>>> = flow {
        emit(openApi.getStatisticsData(userId))
    }
}