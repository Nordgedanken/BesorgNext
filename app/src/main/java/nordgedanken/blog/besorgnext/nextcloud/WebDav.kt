package nordgedanken.blog.besorgnext.nextcloud

import com.thegrizzlylabs.sardineandroid.Sardine
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine


/**
 * Created by MTRNord on 07.01.2019.
 */
object WebDav {
    val server: String? = null
    val username: String? = null
    val password: String? = null
    val sardine: Sardine by lazy {
        val sardineL = OkHttpSardine()
        sardineL.setCredentials(username, password)
        return@lazy sardineL
    }
}