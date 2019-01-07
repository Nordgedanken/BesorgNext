package nordgedanken.blog.besorgnext

import android.app.Application
import nordgedanken.blog.besorgnext.itemSearchList.SearchResultsActivity
import nordgedanken.blog.besorgnext.utils.CSVFile
import org.jetbrains.anko.doAsync

/**
 * Created by MTRNord on 07.01.2019.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        doAsync {
            SearchResultsActivity.pureData = CSVFile(resources.openRawResource(R.raw.autocomplete_data)).read()
        }
    }
}