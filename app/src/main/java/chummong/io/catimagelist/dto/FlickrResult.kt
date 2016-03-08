package chummong.io.catimagelist.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by LeeJongHun on 2016-03-07.
 */
data class FlickrResult(
        val photos: SearchResult,
        val stat: String
)

data class SearchResult(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val total: String,
        @SerializedName("photo")
        val photos: List<Photo>
)

data class Photo(
        val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String
)