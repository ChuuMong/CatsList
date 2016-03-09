package chummong.io.catimagelist.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by LeeJongHun on 2016-03-09.
 */

data class PhotoSearch(
        @SerializedName("current_page")
        @Expose //해당 값이 null일 경우, 필드를 자동 생략
        val currentPage: Long,

        @SerializedName("total_pages")
        @Expose
        val totalPages: Long,

        @SerializedName("total_items")
        @Expose
        val totalItems: Long,

        @SerializedName("photos")
        @Expose
        val photos: ArrayList<Photo>
)