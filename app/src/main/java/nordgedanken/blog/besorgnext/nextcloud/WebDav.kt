package nordgedanken.blog.besorgnext.nextcloud

import okhttp3.HttpUrl
import java.net.MalformedURLException
import java.net.URL


/**
 * Created by MTRNord on 07.01.2019.
 */
object WebDav {
    var server: String? = null
        get() = try {
            URL(field).toString()
        } catch (e: MalformedURLException) {
            URL("https://$field").toString()
        }
    var username: String? = null
    var password: String? = null
    val davBaseAddress: HttpUrl? by lazy { HttpUrl.parse("$server/remote.php/dav/files/$username") }
}