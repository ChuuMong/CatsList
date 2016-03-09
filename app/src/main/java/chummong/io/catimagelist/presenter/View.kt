package chummong.io.catimagelist.presenter

/**
 * Created by LeeJongHun on 2016-03-07.
 */
interface View {
    fun showProgress()

    fun hideProgress()

    fun finish()

    fun networkError(e: Throwable)
}