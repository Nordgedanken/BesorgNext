package nordgedanken.blog.besorgnext.itemSearchList

import com.airbnb.epoxy.TypedEpoxyController
import nordgedanken.blog.besorgnext.utils.Dataset

/**
 * Created by MTRNord on 07.01.2019.
 */
class SearchResultsController : TypedEpoxyController<List<Dataset>>() {
    override fun buildModels(data: List<Dataset>?) {
        for ((index, value) in data?.withIndex()!!) {
            if (value.productName.isNotEmpty()) {
                searchResultItem {
                    id(index)
                    name(value.productName)
                }
            }
        }
    }

    init {
        isDebugLoggingEnabled = true
        setFilterDuplicates(true)
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        super.onExceptionSwallowed(exception)
        throw exception
    }
}