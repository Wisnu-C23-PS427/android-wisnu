package space.mrandika.wisnu
data class Recomedation(
    val id: Int,
    val image: String,
    val city: String,
    val destination: String,
)
object DataDummy {
    val listIcon = listOf(
        R.drawable.montain,
        R.drawable.beach,
        R.drawable.forest,
        R.drawable.montain,
        R.drawable.beach,
        R.drawable.forest,
    )

    val listRecomendation = listOf(
        Recomedation(
            1,
            "https://highlight.id/wp-content/uploads/2018/06/Highlight_tempat-destinasi-wisata-terbaik-paintai-nusa-dua-bali-beaches-tanjung-benoa-travelers_03.jpg",
            "Bali",
            "Pantai Nusa Dua"
        ),
        Recomedation(
            2,
            "https://ik.imagekit.io/tvlk/blog/2021/08/boracay-shutterstock_641355409.png?tr=dpr-2,w-675",
            "Pantai Depok",
            "Depok, Yogyakarta"
        ),
        Recomedation(
            2,
        "https://ik.imagekit.io/tvlk/blog/2021/08/boracay-shutterstock_641355409.png?tr=dpr-2,w-675",
        "Pantai Depok",
        "Depok, Yogyakarta"
    )
    )

    val listEvent = listOf(
        Recomedation(
            1,
            "https://radarmadura.jawapos.com/wp-content/uploads/2022/10/f-karapan-sapi-1ko.jpg",
            "Madura",
            "Karapan Sapi"
        ),
        Recomedation(
            32,
            "https://rm.id/images/berita/med/perjuangan-panjang-reog-ponorogo-di-panggung-dunia-tak-diprioritaskan-di-negeri-sendiri-terancam-diakui-negara-lain_121821.jpeg",
            "Jawa Timur",
            "Reog ponorogo"
        ),
        Recomedation(
            1,
            "https://radarmadura.jawapos.com/wp-content/uploads/2022/10/f-karapan-sapi-1ko.jpg",
            "Madura",
            "Karapan Sapi"
        ),
        Recomedation(
            32,
            "https://rm.id/images/berita/med/perjuangan-panjang-reog-ponorogo-di-panggung-dunia-tak-diprioritaskan-di-negeri-sendiri-terancam-diakui-negara-lain_121821.jpeg",
            "Jawa Timur",
            "Reog ponorogo"
        ),


    )
}