package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class PasswordChangeRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("newPassword")
    val newPassword: String
)
