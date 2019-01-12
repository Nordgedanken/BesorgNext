package nordgedanken.blog.besorgnext.itemSearchList

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.TypedEpoxyController
import nordgedanken.blog.besorgnext.ItemDetailActivity
import nordgedanken.blog.besorgnext.api.ProductsItem

/**
 * Created by MTRNord on 07.01.2019.
 */
class SearchResultsController(val context: Context) : TypedEpoxyController<List<ProductsItem?>>() {
    override fun buildModels(data: List<ProductsItem?>?) {
        for ((index, value) in data?.withIndex()!!) {
            if (value?.productName?.isNotEmpty()!!) {
                searchResultItem {
                    id(index)
                    name(value.productName)
                    category(value.categories)
                    url(value.imageSmallUrl)
                    longClickListener { _ ->
                        val intent = Intent(context, ItemDetailActivity::class.java)
                            .putExtra("TITLE", value.productName)
                            .putExtra("IMAGE", value.imageUrl ?: value.imageSmallUrl)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // Apply activity transition

                            ContextCompat.startActivity(context, intent,
                                ActivityOptions.makeSceneTransitionAnimation(getActivity(context)).toBundle())
                        } else {
                            // Swap without transition
                            ContextCompat.startActivity(context, intent,
                                null)
                        }
                        true
                    }
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

fun getActivity(context: Context?): Activity? {
    if (context == null) {
        return null
    } else if (context is ContextWrapper) {
        return context as? Activity ?: getActivity(context.baseContext)
    }

    return null
}