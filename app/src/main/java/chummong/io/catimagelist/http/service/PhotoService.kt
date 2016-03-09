package chummong.io.catimagelist.http.service

import chummong.io.catimagelist.model.dto.PhotoSearch
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by LeeJongHun on 2016-03-07.
 */
interface PhotoService {

    @GET("photos/search?&term=cats&tag=cat&only=Animals&tags=true&image_size=3")
    fun getPhotoList(@Query("page") page: Int): Observable<PhotoSearch>

    @GET("photos/{photoId}/")
    fun getDetailPhoto(@Path("photoId") id: Int, @Query("image_size") imageSize: Int)
}