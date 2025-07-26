package school.alt.teacher.network.dto

import com.google.gson.annotations.SerializedName

data class MealResponse(
    @SerializedName("exists")
    val exists: Boolean,
    @SerializedName("breakfast")
    val breakfast: Meal?,
    @SerializedName("lunch")
    val lunch: Meal?,
    @SerializedName("dinner")
    val dinner: Meal?
)

data class Meal(
    @SerializedName("mealType")
    val mealType: String,
    @SerializedName("menu")
    val menu: Array<String>,
    @SerializedName("calorie")
    val calorie: String,
    @SerializedName("carbohydrates")
    val carbohydrates: String,
    @SerializedName("protein")
    val protein: String,
    @SerializedName("fat")
    val fat: String,
    @SerializedName("vitaminA")
    val vitaminA: String,
    @SerializedName("thiamin")
    val thiamin: String,
    @SerializedName("riboflavin")
    val riboflavin: String,
    @SerializedName("citaminC")
    val citaminC: String,
    @SerializedName("calcium")
    val calcium: String,
    @SerializedName("iron")
    val iron: String
)