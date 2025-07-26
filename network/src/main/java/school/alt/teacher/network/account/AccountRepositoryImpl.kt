package school.alt.teacher.network.account

import kotlinx.coroutines.flow.flow
import retrofit2.Response
import school.alt.teacher.network.dto.LoginRequest
import school.alt.teacher.network.dto.PasswordChangeRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.SignUpRequestDto
import school.alt.teacher.network.dto.Token
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountRepository {
    override suspend fun signup(signupRequest: SignUpRequestDto): ResponseDto<String> {
        return accountApi.signup(signupRequest)
    }

    override suspend fun login(loginRequest: LoginRequest) = flow {
        emit(accountApi.login(loginRequest))
    }

    override suspend fun refresh(refreshToken: String): Response<ResponseDto<Token>> {
        return accountApi.refresh(refreshToken)
    }

    override suspend fun changePassword(passwordChangeRequest: PasswordChangeRequest): ResponseDto<String> {
        return accountApi.changePassword(passwordChangeRequest)
    }

}