package nordgedanken.blog.besorgnext

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.ISlidePolicy
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import nordgedanken.blog.besorgnext.nextcloud.WebDav
import java.net.MalformedURLException
import java.net.URL

class LoginSlide : Fragment(), ISlidePolicy {
    private val TAG = LoginSlide::class.java.canonicalName
    private var snackbarPresent = false

    override fun isPolicyRespected(): Boolean {
        val baseURL = view?.findViewById<TextInputEditText>(R.id.server_input)?.text?.toString()
        val username = view?.findViewById<TextInputEditText>(R.id.username_input)?.text?.toString()
        val password = view?.findViewById<TextInputEditText>(R.id.password_input)?.text?.toString()
        if (baseURL?.isNotEmpty()!! && username?.isNotEmpty()!! && password?.isNotEmpty()!!) {
            val url = try {
                URL(baseURL)
            } catch (e: MalformedURLException) {
                URL("https://$baseURL")
            }
            WebDav.server = url.toString()
            WebDav.username = username
            WebDav.password = password
            return true
        }
        return false
    }

    override fun onUserIllegallyRequestedNextPage() {
        Log.d(TAG, "Illegal Change")
        if (!snackbarPresent) {
            val contextView = view?.findViewById<CoordinatorLayout>(R.id.snackbar_holder)!!
            val snackbar = Snackbar.make(contextView, R.string.missing_fields_message, Snackbar.LENGTH_LONG).addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    snackbarPresent = false
                }

                override fun onShown(sb: Snackbar?) {
                    super.onShown(sb)
                    snackbarPresent = true
                }
            })
            snackbar.setAction(R.string.ok) {
                snackbar.dismiss()
            }.show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_start, container, false)
    }

    companion object {
        fun newInstance(): LoginSlide {
            return LoginSlide()
        }
    }
}