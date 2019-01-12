package nordgedanken.blog.besorgnext.nextcloud.json

import com.google.gson.annotations.SerializedName

data class JsonData(

	@field:SerializedName("customItems")
	val customItems: List<CustomItemsItem?>? = null,

	@field:SerializedName("currentListItems")
	val currentListItems: List<CurrentListItemsItem?>? = null
)