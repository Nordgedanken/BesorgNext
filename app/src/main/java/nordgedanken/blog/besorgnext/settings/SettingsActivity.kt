package nordgedanken.blog.besorgnext.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nordgedanken.blog.besorgnext.R

/**
 * Created by MTRNord on 03.01.2019.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
    }
}