package nordgedanken.blog.besorgnext.api

import com.google.gson.annotations.SerializedName

data class ProductsItem(

	@field:SerializedName("brands")
	val brands: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("quantity")
	val quantity: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("nutrition_grade_fr")
	val nutritionGradeFr: String? = null,

	@field:SerializedName("image_small_url")
	val imageSmallUrl: String? = null
)