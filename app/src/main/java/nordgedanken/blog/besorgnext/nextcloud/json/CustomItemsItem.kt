package nordgedanken.blog.besorgnext.nextcloud.json

import com.google.gson.annotations.SerializedName

data class CustomItemsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)