package nordgedanken.blog.besorgnext

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.owncloud.android.lib.common.OwnCloudClientFactory
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory
import nordgedanken.blog.besorgnext.nextcloud.Data
import nordgedanken.blog.besorgnext.nextcloud.WebDav
import nordgedanken.blog.besorgnext.nextcloud.json.JsonData
import nordgedanken.blog.besorgnext.settings.SettingsActivity
import org.jetbrains.anko.doAsync

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.canonicalName
    companion object {
        private const val PREFS_NAME = "Prefs"
        private const val PREF_VERSION_CODE_KEY = "version_code"
        private const val DOESNT_EXIST = -1
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val searchView = findViewById<SearchView>(R.id.search_view)
        when (item.itemId) {
            R.id.navigation_lists -> {
                searchView.animate()
                    .alpha(1.0f)
                    .translationY(0F)
                    .withStartAction {searchView.visibility = View.VISIBLE  }.duration = 300
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                searchView.animate()
                    .alpha(0.0f)
                    .translationY(-searchView.height.toFloat())
                    .withEndAction { searchView.visibility = View.GONE }.duration = 300
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                searchView.animate()
                    .alpha(0.0f)
                    .translationY(-searchView.height.toFloat())
                    .withEndAction { searchView.visibility = View.GONE }.duration = 300
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_actionbar, menu)
        clearFocusFix()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            // Check if we're running on Android 5.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Apply activity transition
                startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            } else {
                // Swap without transition
                startActivity(intent,
                    null)
            }
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun checkFirstRun() {
        // Get current version code
        val currentVersionCode = BuildConfig.VERSION_CODE

        // Get saved version code
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

        // Check for first run or upgrade
        when {
            currentVersionCode == savedVersionCode -> { // This is just a normal run
                return
            }
            savedVersionCode == DOESNT_EXIST -> {
                // This is a new install (or the user cleared the shared preferences)
                val intent = Intent(this, IntroActivity::class.java)
                // Swap without transition
                ContextCompat.startActivity(this, intent,
                    null)
                finish()
            }
            currentVersionCode > savedVersionCode -> {
                // This is an upgrade
            }
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply()
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "RESUME")
        invalidateOptionsMenu()
    }

    private fun clearFocusFix() {
        val searchView = findViewById<SearchView>(R.id.search_view)
        val rootView = findViewById<ConstraintLayout>(R.id.content)
        searchView.setQuery("", false)
        searchView.clearFocus()
        rootView.requestFocus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        checkFirstRun()

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (findViewById<SearchView>(R.id.search_view)).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Init Data TODO trigger login
        if (WebDav.password != null && WebDav.server != null && WebDav.username != null) {
            Data.filesDir = this.filesDir
            Data.mClient = OwnCloudClientFactory.createOwnCloudClient(
                Uri.parse(WebDav.server),
                this,
                true)
            Data.mClient?.credentials = OwnCloudCredentialsFactory.newBasicCredentials(WebDav.username, WebDav.password)
            Data.data.observe(this, Observer<JsonData>{
                Log.d(TAG, "WE GOT DATA SYNCED!")
                // TODO display data
            })
            doAsync {
                Data.syncNextcloud()
            }
        } else {
            val intent = Intent(this, IntroActivity::class.java)
            // Swap without transition
            ContextCompat.startActivity(this, intent,
                null)
            finish()
        }
    }
}
