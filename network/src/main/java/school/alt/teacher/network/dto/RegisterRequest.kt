package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("idx")
    val idx: Long,
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("userType")
    val userType: String,
    @SerializedName("grade")
    val grade: Int,
    @SerializedName("className")
    val className: Int,
    @SerializedName("classNo")
    val classNo: Int
)
