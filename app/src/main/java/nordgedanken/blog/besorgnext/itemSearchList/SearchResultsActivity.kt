package nordgedanken.blog.besorgnext.itemSearchList

import android.app.SearchManager
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import nordgedanken.blog.besorgnext.R
import nordgedanken.blog.besorgnext.utils.Dataset

/**
 * Created by MTRNord on 05.01.2019.
 */
class SearchResultsActivity : AppCompatActivity() {
    private val TAG = SearchResultsActivity::class.java.canonicalName

    companion object {
        var pureData: List<Dataset>? = null
    }
    private var recyclerView: EpoxyRecyclerView? = null
    private val searchResultsController by lazy { SearchResultsController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_search_results_page)
        recyclerView = findViewById(R.id.recycler_view)

        val handlerThread = HandlerThread("epoxy")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        EpoxyController.defaultDiffingHandler = handler
        EpoxyController.defaultModelBuildingHandler = handler

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

    inner class Search : AsyncTask<String, Any, List<Dataset>>() {
        override fun doInBackground(vararg params: String?): List<Dataset> {
            return pureData?.asSequence()?.filter { it.productName.contains(params.first()!!) || it.mainCategory.contains(params.first()!!) }?.toList()!!
        }

        override fun onPostExecute(result: List<Dataset>?) {
            searchResultsController.setData(result)
        }
    }
}