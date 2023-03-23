package az.red.presentation.common

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import java.lang.Exception

@SuppressLint("ClickableViewAccessibility")
fun EditText.setDrawableRightTouch(setClickListener: () -> Unit) {
    try {
        this.setOnTouchListener(View.OnTouchListener { _, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= this.right - this.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                    setClickListener()
                    return@OnTouchListener true
                }
            }
            false
        })
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun View.gone() {
    isGone = true
}

fun View.visible() {
    isVisible = true
}

fun View.inVisible() {
    isInvisible = true
}
