package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class SignUpRequestDto(
    @SerializedName("idx")
    val idx: Long? = null,
    @SerializedName("password")
    val password: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("userType")
    val userType: String = "T",
    @SerializedName("grade")
    val grade: Int = 0,
    @SerializedName("className")
    val className: Int = 0,
    @SerializedName("classNo")
    val classNo: Int = 0,
)
