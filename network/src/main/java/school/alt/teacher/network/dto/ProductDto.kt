package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("grantTime")
    val grantTime: String,
    @SerializedName("idx")
    val idx: Int?,
    @SerializedName("price")
    val price: Int,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("image")
    val image: Int?
)