package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class EditUserRequest(
    @SerializedName("allergy")
    val allergy: List<String>? = null,
    @SerializedName("approvedYn")
    val approvedYn: String? = null,
    @SerializedName("className")
    val className: Int? = null,
    @SerializedName("classNo")
    val classNo: Int? = null,
    @SerializedName("createTime")
    val createTime: String? = null,
    @SerializedName("grade")
    val grade: Int? = null,
    @SerializedName("id")
    val id: String?,
    @SerializedName("idx")
    val idx: Int?,
    @SerializedName("image")
    val image: Int? = null,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("point")
    val point: Int? = null,
    @SerializedName("userType")
    val userType: String? = null,
    @SerializedName("withdrawedTime")
    val withdrawedTime: String? = null,
    @SerializedName("withdrawedYn")
    val withdrawedYn: String? = null
)