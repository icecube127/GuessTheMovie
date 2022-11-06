package edu.utap.guessthemovie

data class SongInfo(val name: String, val rawID: Int)

class BGMusic {
    private var songResource = hashMapOf(
        "theme one" to
                SongInfo("theme one", R.raw.themeone),
        "theme two" to
                SongInfo("theme two", R.raw.themetwo)
    )

    fun fetchData(): HashMap<String, SongInfo> {
        return songResource
    }
}