package chummong.io.catimagelist.presenter.main.di

import chummong.io.catimagelist.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by LeeJongHun on 2016-03-11.
 */

@Singleton
@Component(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}