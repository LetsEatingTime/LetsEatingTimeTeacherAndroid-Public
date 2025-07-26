package school.alt.teacher.di

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import school.alt.teacher.di.Constant.BASE_URL
import school.alt.teacher.data.TokenManager
import school.alt.teacher.network.account.AccountApi
import school.alt.teacher.network.dto.ResponseDto
import school.alt.teacher.network.dto.Token
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
) : Authenticator {


    companion object {
        private const val TAG = "AuthAuthenticator"
        var expiredRefreshToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    }


    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { tokenManager.getRefreshToken().first() }
        return runBlocking {
            val reissueToken = reissueToken(refreshToken)

            if (!reissueToken.isSuccessful || reissueToken.body() == null) {
                Log.e(TAG, "Refresh Token 만료")
                tokenManager.deleteData()
                expiredRefreshToken.value = true

                return@runBlocking null
            }

            reissueToken.body()?.let {
                tokenManager.saveAccessToken(it.data.accessToken)
                tokenManager.saveRefreshToken(it.data.refreshToken)

                val token = "Bearer ${it.data.accessToken}"

                response.request.newBuilder()
                    .header("Authorization", token)
                    .build()
            }
        }
    }

    private suspend fun reissueToken(
        refreshToken: String,
    ): retrofit2.Response<ResponseDto<Token>> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AccountApi::class.java)
        return service.refresh("Bearer $refreshToken")
    }
}