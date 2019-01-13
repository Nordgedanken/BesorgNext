package nordgedanken.blog.besorgnext

/**
 * Created by MTRNord on 13.01.2019.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder
import com.github.paolorotolo.appintro.ISlideSelectionListener
import com.github.paolorotolo.appintro.util.LogHelper
import com.github.paolorotolo.appintro.util.TypefaceContainer

abstract class AppIntroBaseFragmentLottie : Fragment(), ISlideSelectionListener, ISlideBackgroundColorHolder {

    private var animation: Int = 0
    private var bgColor: Int = 0
    private var titleColor: Int = 0
    private var descColor: Int = 0
    private val layoutId: Int = 0
    private var title: String? = null
    private var description: String? = null
    private var titleTypeface: TypefaceContainer? = null
    private var descTypeface: TypefaceContainer? = null

    private var mainLayout: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        if (arguments != null && arguments!!.size() != 0) {
            val argsTitleTypeface = arguments!!.getString(ARG_TITLE_TYPEFACE)
            val argsDescTypeface = arguments!!.getString(ARG_DESC_TYPEFACE)

            val argsTitleTypefaceRes = arguments!!.getInt(ARG_TITLE_TYPEFACE_RES)
            val argsDescTypefaceRes = arguments!!.getInt(ARG_DESC_TYPEFACE_RES)

            animation = arguments!!.getInt(ARG_ANIMATION)

            title = arguments!!.getString(ARG_TITLE)
            description = arguments!!.getString(ARG_DESC)
            titleTypeface = TypefaceContainer(argsTitleTypeface, argsTitleTypefaceRes)
            descTypeface = TypefaceContainer(argsDescTypeface, argsDescTypefaceRes)

            bgColor = arguments!!.getInt(ARG_BG_COLOR)
            titleColor = if (arguments!!.containsKey(ARG_TITLE_COLOR))
                arguments!!.getInt(ARG_TITLE_COLOR)
            else
                0
            descColor = if (arguments!!.containsKey(ARG_DESC_COLOR))
                arguments!!.getInt(ARG_DESC_COLOR)
            else
                0
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            animation = savedInstanceState.getInt(ARG_ANIMATION)
            title = savedInstanceState.getString(ARG_TITLE)
            description = savedInstanceState.getString(ARG_DESC)

            titleTypeface = TypefaceContainer(
                savedInstanceState.getString(ARG_TITLE_TYPEFACE),
                savedInstanceState.getInt(ARG_TITLE_TYPEFACE_RES, 0)
            )
            descTypeface = TypefaceContainer(
                savedInstanceState.getString(ARG_DESC_TYPEFACE),
                savedInstanceState.getInt(ARG_DESC_TYPEFACE_RES, 0)
            )

            bgColor = savedInstanceState.getInt(ARG_BG_COLOR)
            titleColor = savedInstanceState.getInt(ARG_TITLE_COLOR)
            descColor = savedInstanceState.getInt(ARG_DESC_COLOR)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)
        val titleText = view.findViewById<TextView>(R.id.title)
        val descriptionText = view.findViewById<TextView>(R.id.description)
        val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)
        mainLayout = view.findViewById(R.id.main)

        titleText.text = title
        if (titleColor != 0) {
            titleText.setTextColor(titleColor)
        }
        titleTypeface!!.applyTo(titleText)
        titleTypeface!!.applyTo(descriptionText)
        descriptionText.text = description
        if (descColor != 0) {
            descriptionText.setTextColor(descColor)
        }
        animationView.setAnimation(animation)
        animationView.scale = 5f
        animationView.speed = 0.5f
        animationView.playAnimation()
        animationView.repeatCount = LottieDrawable.INFINITE
        mainLayout!!.setBackgroundColor(bgColor)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ARG_ANIMATION, animation)
        outState.putString(ARG_TITLE, title)
        outState.putString(ARG_DESC, description)
        outState.putInt(ARG_BG_COLOR, bgColor)
        outState.putInt(ARG_TITLE_COLOR, titleColor)
        outState.putInt(ARG_DESC_COLOR, descColor)
        saveTypefacesInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    private fun saveTypefacesInstanceState(outState: Bundle) {
        if (titleTypeface != null) {
            outState.putString(ARG_TITLE_TYPEFACE, titleTypeface!!.typeFaceUrl)
            outState.putInt(ARG_TITLE_TYPEFACE_RES, titleTypeface!!.typeFaceResource)
        }
        if (descTypeface != null) {
            outState.putString(ARG_DESC_TYPEFACE, descTypeface!!.typeFaceUrl)
            outState.putInt(ARG_DESC_TYPEFACE_RES, descTypeface!!.typeFaceResource)
        }
    }

    override fun onSlideDeselected() {
        LogHelper.d(TAG, String.format("Slide %s has been deselected.", title))
    }

    override fun onSlideSelected() {
        LogHelper.d(TAG, String.format("Slide %s has been selected.", title))
    }

    override fun getDefaultBackgroundColor(): Int {
        return bgColor
    }

    override fun setBackgroundColor(@ColorInt backgroundColor: Int) {
        mainLayout!!.setBackgroundColor(backgroundColor)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    companion object {
        @JvmStatic protected val ARG_TITLE = "title"
        @JvmStatic protected val ARG_TITLE_TYPEFACE = "title_typeface"
        @JvmStatic protected val ARG_TITLE_TYPEFACE_RES = "title_typeface_res"
        @JvmStatic protected val ARG_DESC = "desc"
        @JvmStatic protected val ARG_DESC_TYPEFACE = "desc_typeface"
        @JvmStatic protected val ARG_DESC_TYPEFACE_RES = "desc_typeface_res"
        @JvmStatic protected val ARG_ANIMATION = "animation"
        @JvmStatic protected val ARG_BG_COLOR = "bg_color"
        @JvmStatic protected val ARG_TITLE_COLOR = "title_color"
        @JvmStatic protected val ARG_DESC_COLOR = "desc_color"

        private val TAG = LogHelper.makeLogTag(AppIntroBaseFragmentLottie::class.java)
    }
}