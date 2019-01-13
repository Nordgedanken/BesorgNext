package nordgedanken.blog.besorgnext

import android.os.Bundle

import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import androidx.annotation.RawRes

class AppIntroFragmentLottie : AppIntroBaseFragmentLottie() {

    override fun getLayoutId(): Int {
        return R.layout.appintro_fragment_intro
    }

    companion object {

        /**
         * Generates new instance for AppIntroFragment
         *
         * @param title            CharSequence which will be the slide title
         * @param titleTypefaceUrl CharSequence the URL of the custom typeface for
         * the title found at assets folder
         * @param description      CharSequence which will be the slide description
         * @param descTypefaceUrl  CharSequence the URL of the custom typeface for
         * the description found at assets folder
         * @param animationRaw    @RawRes (Integer) the animation that will be
         * displayed, obtained from Resources
         * @param bgColor          @ColorInt (Integer) custom background color
         * @return AppIntroFragment created instance
         */
        fun newInstance(
            title: CharSequence,
            titleTypefaceUrl: String?,
            description: CharSequence,
            descTypefaceUrl: String?,
            @RawRes animationRaw: Int,
            @ColorInt bgColor: Int,
            @ColorInt titleColor: Int,
            @ColorInt descColor: Int
        ): AppIntroFragmentLottie {
            val sliderPage = SliderPageLottie()
            sliderPage.title = title
            sliderPage.titleTypeface = titleTypefaceUrl
            sliderPage.description = description
            sliderPage.descTypeface = descTypefaceUrl
            sliderPage.animationRaw = animationRaw
            sliderPage.bgColor = bgColor
            sliderPage.titleColor = titleColor
            sliderPage.descColor = descColor
            return newInstance(sliderPage)
        }

        /**
         * Generates new instance for AppIntroFragment
         *
         * @param title            CharSequence which will be the slide title
         * @param titleTypefaceRes @FontRes (Integer) custom title typeface obtained
         * from Resources
         * @param description      CharSequence which will be the slide description
         * @param descTypefaceRes  @FontRes (Integer) custom description typeface
         * obtained from Resources
         * @param animationRaw    @RawRes (Integer) the animation that will be
         * displayed, obtained from Resources
         * @param bgColor          @ColorInt (Integer) custom background color
         * @param titleColor       @ColorInt (Integer) custom title color
         * @param descColor        @ColorInt (Integer) custom description color
         * @return AppIntroFragment created instance
         */
        fun newInstance(
            title: CharSequence,
            @FontRes titleTypefaceRes: Int,
            description: CharSequence,
            @FontRes descTypefaceRes: Int,
            @RawRes animationRaw: Int,
            @ColorInt bgColor: Int,
            @ColorInt titleColor: Int,
            @ColorInt descColor: Int
        ): AppIntroFragmentLottie {
            val sliderPage = SliderPageLottie()
            sliderPage.title = title
            sliderPage.titleTypefaceFontRes = titleTypefaceRes
            sliderPage.description = description
            sliderPage.descTypefaceFontRes = descTypefaceRes
            sliderPage.animationRaw = animationRaw
            sliderPage.bgColor = bgColor
            sliderPage.titleColor = titleColor
            sliderPage.descColor = descColor
            return newInstance(sliderPage)
        }

        /**
         * Generates an AppIntroFragment by a given SliderPage
         *
         * @param sliderPage SliderPage which contains all attributes for the
         * current slide
         * @return AppIntroFragment created instance
         */
        fun newInstance(sliderPage: SliderPageLottie): AppIntroFragmentLottie {
            val slide = AppIntroFragmentLottie()
            val args = Bundle()

            args.putString(AppIntroBaseFragmentLottie.ARG_TITLE, sliderPage.titleString)
            args.putString(AppIntroBaseFragmentLottie.ARG_TITLE_TYPEFACE, sliderPage.titleTypeface)
            args.putInt(AppIntroBaseFragmentLottie.ARG_TITLE_TYPEFACE_RES, sliderPage.titleTypefaceFontRes)
            args.putInt(AppIntroBaseFragmentLottie.ARG_TITLE_COLOR, sliderPage.titleColor)

            args.putString(AppIntroBaseFragmentLottie.ARG_DESC, sliderPage.descriptionString)
            args.putString(AppIntroBaseFragmentLottie.ARG_DESC_TYPEFACE, sliderPage.descTypeface)
            args.putInt(AppIntroBaseFragmentLottie.ARG_DESC_TYPEFACE_RES, sliderPage.descTypefaceFontRes)
            args.putInt(AppIntroBaseFragmentLottie.ARG_DESC_COLOR, sliderPage.descColor)

            args.putInt(AppIntroBaseFragmentLottie.ARG_ANIMATION, sliderPage.animationRaw)
            args.putInt(AppIntroBaseFragmentLottie.ARG_BG_COLOR, sliderPage.bgColor)

            slide.arguments = args
            return slide
        }
    }
}