package nordgedanken.blog.besorgnext.itemSearchList

import com.airbnb.epoxy.TypedEpoxyController
import nordgedanken.blog.besorgnext.api.ProductsItem

/**
 * Created by MTRNord on 07.01.2019.
 */
class SearchResultsController : TypedEpoxyController<List<ProductsItem?>>() {
    override fun buildModels(data: List<ProductsItem?>?) {
        for ((index, value) in data?.withIndex()!!) {
            if (value?.productName?.isNotEmpty()!!) {
                searchResultItem {
                    id(index)
                    name(value.productName)
                    category(value.categories)
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