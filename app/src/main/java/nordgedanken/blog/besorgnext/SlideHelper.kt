package nordgedanken.blog.besorgnext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import nordgedanken.blog.besorgnext.nextcloud.WebDav
import java.net.MalformedURLException
import java.net.URL

class SlideHelper : Fragment() {
    private var layoutResId: Int = 0
    private val TAG = SlideHelper::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null && arguments!!.containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = arguments!!.getInt(ARG_LAYOUT_RES_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)

        if (layoutResId == R.layout.login_start) {
            view.findViewById<MaterialButton>(R.id.startLogin).setOnClickListener {
                val baseURL = view?.findViewById<TextInputEditText>(R.id.server_input)?.text?.toString()
                val username = view?.findViewById<TextInputEditText>(R.id.username_input)?.text?.toString()
                val password = view?.findViewById<TextInputEditText>(R.id.password_input)?.text?.toString()
                val url = try {
                    URL(baseURL)
                } catch (e: MalformedURLException) {
                    URL("https://$baseURL")
                }
                WebDav.server = url.toString()
                WebDav.username = username
                WebDav.password = password
                callback?.invoke()
            }
        }
        return view!!
    }

    companion object {

        private val ARG_LAYOUT_RES_ID = "layoutResId"
        private var callback: (()->Unit)? = null

        fun newInstance(layoutResId: Int, callback: (()->Unit)? = null): SlideHelper {
            val slide = SlideHelper()

            this.callback = callback

            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            slide.arguments = args

            return slide
        }
    }
}