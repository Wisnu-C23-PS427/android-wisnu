package space.mrandika.wisnu.ui.poi.detail

import space.mrandika.wisnu.model.guide.Guide
import space.mrandika.wisnu.model.poi.POIResponse

data class POIDetailUiState (
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val isEmpty : Boolean = false,
    val DetailResult : POIResponse? = null,
    val GuideResult : Guide? = null
)