package space.mrandika.wisnu.utils

import android.view.View

object ViewUtils {
    fun hideViews(vararg views: View?) {
        views.forEach { view ->
            view?.visibility = View.GONE
        }
    }

    fun showViews(vararg views: View?) {
        views.forEach { view ->
            view?.visibility = View.VISIBLE
        }
    }
}