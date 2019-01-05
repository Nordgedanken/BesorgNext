package nordgedanken.blog.besorgnext

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nextcloud_login.*
import java.net.URI
import java.util.*


class NextcloudLogin : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nextcloud_login)
        startLogin(intent.getStringExtra("baseURL"))
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startLogin(baseUrl: String) {
        webView.webViewClient = ViewerWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.userAgentString = "BesorgNext"
        webView.clearCache(true)
        webView.clearHistory()
        val header = mutableMapOf<String, String>()
        header["OCS-APIREQUEST"] = "true"
        header["ACCEPT_LANGUAGE"] = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList.getDefault()[0].language
        } else {
            Locale.getDefault().language
        }
        webView.loadUrl("$baseUrl/index.php/login/flow", header)
    }
}

private class ViewerWebViewClient : WebViewClient() {
    private val TAG = ViewerWebViewClient::class.java.canonicalName

    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val uri = URI.create(url)
        if (uri.scheme == "nc") {
            parseUri(uri)
            return true
        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(webView: WebView, request: WebResourceRequest): Boolean {
        val uri = URI.create(request.url.toString())
        if (uri.scheme == "nc") {
            parseUri(uri)
            return true
        }
        return super.shouldOverrideUrlLoading(webView, request)
    }

    private fun parseUri(uri: URI) {
        val server = uri.toASCIIString().substringAfter("nc://login/server:").substringBefore('&')
        val user = uri.toASCIIString().substringAfter("user:").substringBefore('&')
        val password = uri.toASCIIString().substringAfter("password:")
        Log.d(TAG, "${uri.toASCIIString()}\nServer: $server\nUser: $user\nPassword: $password")
    }
}