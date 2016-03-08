package chummong.io.catimagelist

import android.app.Application
import android.content.Context

/**
 * Created by LeeJongHun on 2016-03-07.
 */


class App : Application() {

    companion object {
        var CONTEXT: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        CONTEXT = applicationContext
    }
}