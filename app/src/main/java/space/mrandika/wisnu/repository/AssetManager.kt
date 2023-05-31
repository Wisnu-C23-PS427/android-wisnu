package space.mrandika.wisnu.repository

import android.content.Context
import space.mrandika.wisnu.utils.getJsonAsString
import javax.inject.Inject

class AssetManager @Inject constructor(
    private val context: Context
) {
    fun getStringJson(resId: Int): String {
        return getJsonAsString(context, resId)
    }
}