package chummong.io.catimagelist.presenter.main

import chummong.io.catimagelist.dto.Photo
import chummong.io.catimagelist.presenter.View

/**
 * Created by LeeJongHun on 2016-03-07.
 */

interface MainView : View {
    fun showCatsList(photos: List<Photo>)
}