package nordgedanken.blog.besorgnext.nextcloud

import com.thegrizzlylabs.sardineandroid.Sardine
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import okhttp3.HttpUrl


/**
 * Created by MTRNord on 07.01.2019.
 */
object WebDav {
    val server: String? = null
    val username: String? = null
    val password: String? = null
    val davBaseAddress = HttpUrl.parse("$server/remote.php/dav/files/$username")
    val sardine: Sardine by lazy {
        val sardineL = OkHttpSardine()
        sardineL.setCredentials(username, password)
        return@lazy sardineL
    }
}