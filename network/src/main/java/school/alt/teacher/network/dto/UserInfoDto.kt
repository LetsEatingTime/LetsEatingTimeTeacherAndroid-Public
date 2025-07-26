package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("idx")
    val idx: Long? = -1,
    @SerializedName("image")
    val image: Long? = -1,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("createTime")
    val createTime: String? = "",
    @SerializedName("userType")
    val userType: String? = "",
    @SerializedName("grade")
    val grade: Int = 0,
    @SerializedName("className")
    val className: Int = 0,
    @SerializedName("classNo")
    val classNo: Int = 0,
    @SerializedName("approvedYn")
    val approvedYn: String? = "",
    @SerializedName("withdrawedYn")
    val withdrawedYn: String? = "",
    @SerializedName("withdrawedTime")
    val withdrawedTime: String? = "",
    @SerializedName("allergy")
    val allergy: String? = "", //List<String>? = emptyList<String>(),
    @SerializedName("point")
    val point: Int? = -1
)
