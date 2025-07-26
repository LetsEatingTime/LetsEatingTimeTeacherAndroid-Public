package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: T
)
