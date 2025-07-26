package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
