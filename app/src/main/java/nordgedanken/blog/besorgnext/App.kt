package nordgedanken.blog.besorgnext

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import com.airbnb.epoxy.EpoxyController

/**
 * Created by MTRNord on 07.01.2019.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val handlerThread = HandlerThread("epoxy")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        EpoxyController.defaultDiffingHandler = handler
        EpoxyController.defaultModelBuildingHandler = handler
    }
}