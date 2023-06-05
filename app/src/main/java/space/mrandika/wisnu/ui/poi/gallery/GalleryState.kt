package space.mrandika.wisnu.ui.poi.gallery

import space.mrandika.wisnu.model.gallery.Gallery

data class GalleryState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val isEmpty : Boolean = false,
    val gallery : List<Gallery>? = emptyList()
)
