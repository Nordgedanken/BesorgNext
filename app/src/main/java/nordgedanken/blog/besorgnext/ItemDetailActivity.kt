package nordgedanken.blog.besorgnext

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView

class ItemDetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val title = intent.getStringExtra("TITLE")
        val url = intent.getStringExtra("IMAGE")
        this.title = title
        val image = findViewById<ImageView>(R.id.image)
        GlideApp.with(this)
            .load(url)
            .placeholder(R.drawable.search_placeholder_image)
            .fitCenter()
            .into(image)
    }
}
