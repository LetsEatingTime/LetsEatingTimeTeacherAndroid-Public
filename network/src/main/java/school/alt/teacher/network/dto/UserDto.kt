package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("mealType")
    val mealType: List<String>,
    @SerializedName("user")
    val user: UserInfoDto
)