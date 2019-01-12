package nordgedanken.blog.besorgnext

import android.app.ActivityOptions
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import nordgedanken.blog.besorgnext.nextcloud.Data
import nordgedanken.blog.besorgnext.nextcloud.WebDav
import nordgedanken.blog.besorgnext.nextcloud.json.JsonData
import nordgedanken.blog.besorgnext.settings.SettingsActivity


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.canonicalName

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

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "RESUME")
        clearFocusFix()
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

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (findViewById<SearchView>(R.id.search_view)).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Init Data TODO trigger login
        if (WebDav.password != null || WebDav.server != null || WebDav.username != null) {
            Data.filesDir = this.filesDir
            Data.syncNextcloud()
            Data.data.observe(this, Observer<JsonData>{
                Log.d(TAG, "WE GOT DATA SYNCED!")
                // TODO display data
            })
        } else {
            val contextView = findViewById<CoordinatorLayout>(R.id.snackbar_holder)
            Snackbar.make(contextView, R.string.login_required, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}
