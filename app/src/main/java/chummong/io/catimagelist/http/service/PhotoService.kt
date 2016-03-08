package chummong.io.catimagelist.http.service

import chummong.io.catimagelist.dto.FlickrResult
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by LeeJongHun on 2016-03-07.
 */
interface PhotoService {

    @GET("?method=flickr.photos.search&text=cat")
    fun getCatList(@Query("per_page") page: Int): Observable<FlickrResult>
}