package nordgedanken.blog.besorgnext

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage

/**
 * Created by MTRNord on 12.01.2019.
 */
class IntroActivity : AppIntro() {
    private val TAG = IntroActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Note here that we DO NOT use setContentView();

        // First Page
        val firstPage = SliderPage()
        firstPage.title = getString(R.string.welcome)
        firstPage.description = getString(R.string.welcome_text)
        //sliderPage.bgColor = Color.parseColor()
        addSlide(AppIntroFragment.newInstance(firstPage))

        // Login Page
        addSlide(LoginSlide.newInstance())

        // Done Page
        val donePage = SliderPage()
        donePage.title = getString(R.string.finished)
        donePage.description = getString(R.string.done_text)
        //sliderPage.bgColor = Color.parseColor()
        addSlide(AppIntroFragment.newInstance(donePage))

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"))
        setSeparatorColor(Color.parseColor("#2196F3"))

        // Hide Skip/Done button.
        showSkipButton(false)
        wizardMode = true
        setProgressButtonEnabled(true)
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        val intent = Intent(this, MainActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            ContextCompat.startActivity(this, intent,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        } else {
            // Swap without transition
            ContextCompat.startActivity(this, intent,
                null)
        }
    }

}