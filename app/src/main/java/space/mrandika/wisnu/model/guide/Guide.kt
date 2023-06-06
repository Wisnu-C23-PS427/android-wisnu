package space.mrandika.wisnu.model.guide

import com.google.gson.annotations.SerializedName
import space.mrandika.wisnu.model.review.Review

data class Guide(
    @field:SerializedName("end_date")
    val endDate: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("start_date")
    val startDate: String? = null,

    @field:SerializedName("time_duration_in_min")
    val timeDurationInMin: Int? = null,

    @field:SerializedName("avg_star")
    val avgStar: Any? = null,

    @field:SerializedName("reviews")
    val reviews: List<Review?>? = null,

    @field:SerializedName("price")
    val price: Int? = null,
)