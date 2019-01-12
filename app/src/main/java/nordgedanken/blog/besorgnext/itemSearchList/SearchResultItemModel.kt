package nordgedanken.blog.besorgnext.itemSearchList

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import nordgedanken.blog.besorgnext.GlideApp
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

    @EpoxyAttribute
    var url: String? = null

    @EpoxyAttribute
    var longClickListener: View.OnLongClickListener? = null

    override fun bind(holder: Holder) {
        holder.productName.text = name
        holder.productImage.contentDescription = name
        GlideApp.with(holder.productImage)
            .load(url)
            .placeholder(R.drawable.search_placeholder_image)
            .transforms(RoundedCorners(5))
            //.fitCenter()
            .into(holder.productImage)
        holder.category.text = category
        holder.topLayout.setOnLongClickListener(longClickListener)
    }

    override fun shouldSaveViewState(): Boolean {
        return true
    }
}

class Holder : KotlinEpoxyHolder() {
    val topLayout by bind<RelativeLayout>(R.id.top_layout)
    val productName by bind<TextView>(R.id.product_name)
    val category by bind<TextView>(R.id.category)
    val productImage by bind<ImageView>(R.id.product_image)
}