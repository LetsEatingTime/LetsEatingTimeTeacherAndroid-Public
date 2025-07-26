package school.alt.teacher.network.account

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import school.alt.teacher.network.dto.LoginRequest
import school.alt.teacher.network.dto.PasswordChangeRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.SignUpRequestDto
import school.alt.teacher.network.dto.Token

interface AccountRepository {
    suspend fun signup(signupRequest: SignUpRequestDto): ResponseDto<String>
    suspend fun login(loginRequest: LoginRequest): Flow<ResponseDto<Token>>
    suspend fun refresh(refreshToken: String): Response<ResponseDto<Token>>
    suspend fun changePassword(passwordChangeRequest: PasswordChangeRequest): ResponseDto<String>

}