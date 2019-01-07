package nordgedanken.blog.besorgnext.api

import com.google.gson.annotations.SerializedName

data class Products(

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("skip")
	val skip: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null,

	@field:SerializedName("page_size")
	val pageSize: String? = null
)