package nordgedanken.blog.besorgnext.itemSearchList

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import nordgedanken.blog.besorgnext.R
import nordgedanken.blog.besorgnext.utils.CSVFile

/**
 * Created by MTRNord on 05.01.2019.
 */
class SearchResultsActivity : AppCompatActivity() {
    private val TAG = SearchResultsActivity::class.java.canonicalName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_search_results_page)

        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            searchArticles(query)
        }
    }

    private fun searchArticles(query: String) {
        val raw = resources.openRawResource(R.raw.autocomplete_data)
        val data = CSVFile(raw).read()
        val searchResults = data.filter { it.productName.contains(query) || it.mainCategory.contains(query) }
        val recyclerView = findViewById<EpoxyRecyclerView>(R.id.recycler_view)
        recyclerView.withModels {
            for ((index, value) in searchResults.withIndex()) {
                if (value.productName.isNotEmpty()) {
                    searchResultItem {
                        id(index)
                        name(value.productName)
                    }
                }
            }
        }
    }
}

/** Easily add models to an EpoxyRecyclerView, the same way you would in a buildModels method of EpoxyController. */
fun EpoxyRecyclerView.withModels(buildModelsCallback: EpoxyController.() -> Unit) {
    setControllerAndBuildModels(object : EpoxyController() {
        override fun buildModels() {
            buildModelsCallback()
        }
    })
}