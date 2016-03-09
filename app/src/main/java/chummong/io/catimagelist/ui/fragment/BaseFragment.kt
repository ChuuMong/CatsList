package chummong.io.catimagelist.ui.fragment

import android.support.v4.app.Fragment

/**
 * Created by LeeJongHun on 2016-03-07.
 */
abstract class BaseFragment : Fragment() {

    abstract fun showNetworkErrorDialog(msg: String)
}