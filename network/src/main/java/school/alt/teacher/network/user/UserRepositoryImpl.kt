package school.alt.teacher.network.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import school.alt.teacher.network.dto.ResponseDto
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : UserRepository {
    override suspend fun withdraw(): ResponseDto<String> {
        return userApi.withdraw()
    }

    override suspend fun getProfile() = flow {
        emit(userApi.getProfile())
    }

    override suspend fun getUserImage(idx: Int) = flow {
        emit(userApi.getImage(idx))
    }

    override suspend fun getStudentMealAnalysis(idx: Int): Flow<String> = flow {
        emit(userApi.getStudentMealAnalysis(idx))
    }

}