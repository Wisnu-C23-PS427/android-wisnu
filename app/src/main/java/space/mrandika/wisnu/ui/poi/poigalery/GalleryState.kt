package space.mrandika.wisnu.ui.poi.poigalery

import space.mrandika.wisnu.model.gallery.Gallery
import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POIResponse

data class GalleryState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val isEmpty : Boolean = false,
    val gallery : List<Gallery>? = emptyList()
)
