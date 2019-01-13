package nordgedanken.blog.besorgnext

import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import androidx.annotation.RawRes

/**
 * Slide Page Model
 */
data class SliderPageLottie @JvmOverloads constructor(
    var title: CharSequence? = null,
    var description: CharSequence? = null,
    @RawRes var animationRaw: Int = 0,
    @ColorInt var bgColor: Int = 0,
    @ColorInt var titleColor: Int = 0,
    @ColorInt var descColor: Int = 0,
    @FontRes var titleTypefaceFontRes: Int = 0,
    @FontRes var descTypefaceFontRes: Int = 0,
    var titleTypeface: String? = null,
    var descTypeface: String? = null
) {
    val titleString: String? get() = title?.toString()
    val descriptionString: String? get() = description?.toString()
}