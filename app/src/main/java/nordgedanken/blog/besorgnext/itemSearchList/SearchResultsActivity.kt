package nordgedanken.blog.besorgnext.itemSearchList

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
    val TAG = SearchResultsActivity::class.java.canonicalName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_search_results_page)
        val recyclerView = findViewById<EpoxyRecyclerView>(R.id.recycler_view)
        val raw = resources.openRawResource(R.raw.autocomplete_data)
        val data = CSVFile(raw).read()
        recyclerView.withModels {
            for ((index, value) in data.withIndex()) {
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