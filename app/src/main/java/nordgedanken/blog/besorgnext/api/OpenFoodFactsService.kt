package nordgedanken.blog.besorgnext.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by MTRNord on 07.01.2019.
 */

object OpenFoodFactsApi {
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://de.openfoodfacts.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create<OpenFoodFactsService>(OpenFoodFactsService::class.java)
}

interface OpenFoodFactsService {
    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=image_url,categories,image_small_url,product_name,brands,quantity,code,nutrition_grade_fr}")
    fun getProduct(@Query("search_terms") product: String): Call<Products>
}