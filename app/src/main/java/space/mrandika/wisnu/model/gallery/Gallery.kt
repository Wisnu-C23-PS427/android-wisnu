package space.mrandika.wisnu.model.gallery

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Gallery(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("is_from_wisnu_team")
	val isFromWisnuTeam: Boolean? = null,

	@field:SerializedName("is_vr_capable")
	val isVrCapable: Boolean? = null
): Serializable
