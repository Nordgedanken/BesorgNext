package nordgedanken.blog.besorgnext.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import nordgedanken.blog.besorgnext.R

/**
 * Created by MTRNord on 03.01.2019.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey)
    }
}