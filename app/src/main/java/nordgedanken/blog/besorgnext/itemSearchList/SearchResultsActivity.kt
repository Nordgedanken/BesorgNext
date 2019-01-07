package nordgedanken.blog.besorgnext.itemSearchList

import android.app.SearchManager
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyRecyclerView
import nordgedanken.blog.besorgnext.R
import nordgedanken.blog.besorgnext.api.OpenFoodFactsApi
import nordgedanken.blog.besorgnext.api.ProductsItem

/**
 * Created by MTRNord on 05.01.2019.
 */
class SearchResultsActivity : AppCompatActivity() {
    private val TAG = SearchResultsActivity::class.java.canonicalName

    private var recyclerView: EpoxyRecyclerView? = null
    private val searchResultsController by lazy { SearchResultsController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_search_results_page)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView?.setController(searchResultsController)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        Log.d(TAG, "here")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            Search().execute(query)
        }
    }

    inner class Search : AsyncTask<String, Any, List<ProductsItem?>>() {
        override fun doInBackground(vararg params: String?): List<ProductsItem?> {
            val request = OpenFoodFactsApi.service.getProduct(params.first()!!).execute()
            if (request.isSuccessful) {
                return request.body()?.products!!
            }
            return emptyList()
        }

        override fun onPostExecute(result: List<ProductsItem?>?) {
            searchResultsController.setData(result)
        }
    }
}