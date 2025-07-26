package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class AwarenessMenu(
    @SerializedName("foodName")
    val foodName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("totalConsumption")
    val totalConsumption: Int,
    @SerializedName("totalLeft")
    val totalLeft: Int
)