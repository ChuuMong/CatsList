package chummong.io.catimagelist.presenter.main.di

import chummong.io.catimagelist.presenter.main.MainPersenter
import chummong.io.catimagelist.presenter.main.MainPersenterImpl
import chummong.io.catimagelist.presenter.main.MainView
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by LeeJongHun on 2016-03-11.
 */

@Module
class MainModule(private val view: MainView) {

    @Provides
    @Singleton
    fun providePresenter(mainPersenter: MainPersenterImpl): MainPersenter {
        return mainPersenter
    }

    @Provides
    @Singleton
    fun provideView(): MainView {
        return view
    }
}