package nordgedanken.blog.besorgnext.itemSearchList

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import nordgedanken.blog.besorgnext.R
import nordgedanken.blog.besorgnext.utils.KotlinEpoxyHolder


/**
 * Created by MTRNord on 05.01.2019.
 */
@EpoxyModelClass(layout = R.layout.search_result)
abstract class SearchResultItemModel : EpoxyModelWithHolder<Holder>() {
    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var category: String? = null
    override fun bind(holder: Holder) {
        holder.productName.text = name
        holder.category.text = category
    }

    override fun shouldSaveViewState(): Boolean {
        return true
    }
}

class Holder : KotlinEpoxyHolder() {
    val productName by bind<TextView>(R.id.product_name)
    val category by bind<TextView>(R.id.category)
}