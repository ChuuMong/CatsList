package chummong.io.catimagelist.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Photo(
        @SerializedName("id")
        @Expose
        val id: Long,

        @SerializedName("name")
        @Expose
        val title: String,

        @SerializedName("description")
        @Expose
        val description: String,

        @SerializedName("tags")
        val tags: List<String>,

        @SerializedName("camera")
        @Expose
        val camera: String,

        @SerializedName("focal_length")
        @Expose
        val focalLength: String,

        @SerializedName("iso")
        @Expose
        val iso: String,

        @SerializedName("shutter_speed")
        @Expose
        val shutterSpeed: String,

        @SerializedName("aperture")
        @Expose
        val aperture: String,

        @SerializedName("created_at")
        @Expose
        val createdAt: String,

        @SerializedName("latitude")
        @Expose
        val latitude: Double,

        @SerializedName("longitude")
        @Expose
        val longitude: Double,

        @SerializedName("votes_count")
        @Expose
        val votesCount: Long,

        @SerializedName("favorites_count")
        @Expose
        val favoritesCount: Long,

        @SerializedName("comments_count")
        @Expose
        val commentsCount: Long,

        @SerializedName("rating")
        @Expose
        val rating: Float,

        @SerializedName("image_url")
        @Expose
        val imageUrl: String
) {
    override fun hashCode(): Int {
        return id.toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Photo) {
            return other.id == this.id
        }

        return false
    }
}