package school.alt.teacher.network.account

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import school.alt.teacher.network.dto.LoginRequest
import school.alt.teacher.network.dto.PasswordChangeRequest
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.SignUpRequestDto
import school.alt.teacher.network.dto.Token

interface AccountApi {
    @Headers("Auth: false")
    @POST("account/signup.do")
    suspend fun signup(@Body signupRequest: SignUpRequestDto): ResponseDto<String>

    @Headers("Auth: false")
    @POST("account/login.do")
    suspend fun login(@Body loginRequest: LoginRequest): ResponseDto<Token>

    @Headers("Auth: false")
    @POST("account/refresh.do")
    suspend fun refresh(@Header("Authorization") refreshToken: String): Response<ResponseDto<Token>>

    @Headers("Auth: false")
    @POST("account/pw-change")
    suspend fun changePassword(@Body passwordChangeRequest: PasswordChangeRequest): ResponseDto<String>
}