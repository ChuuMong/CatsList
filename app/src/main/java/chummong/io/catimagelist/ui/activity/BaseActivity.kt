package chummong.io.catimagelist.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.Service
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by LeeJongHun on 2016-03-07.
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Activity Show
     */
    protected fun showActivity(activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }

    /**
     * Activity Show

     * @param extraIntent Extra Data
     */
    protected fun showActivity(activity: Class<out Activity>, extraIntent: Intent) {
        //        Intent intent = new Intent(this, activity);
        //        intent.putExtras(extraIntent.getExtras());
        //        startActivity(intent);

        startActivity(Intent(this, activity).putExtras(extraIntent.extras))
    }

    protected fun showActivity(activity: Class<out Activity>, flag: Int) {
        startActivity(Intent(this, activity).addFlags(flag))
    }

    protected fun showActivityFinish(activity: Class<out Activity>) {
        showActivity(activity)
        finish()
    }

    protected fun showActivityFinish(activity: Class<out Activity>, flag: Int) {
        showActivity(activity, flag)
        finish()
    }

    /**
     * 서비스 시작
     */
    protected fun startService(service: Class<out Service>) {
        startService(Intent(this, service))
    }

    /**
     * Fragment Add
     */
    protected fun addFragment(contentFrame: Int, addFragment: Fragment, isAdd: Boolean, tag: String? = null, animIn: Int = -1,
                              animOut: Int = -1) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (animIn != -1 && animOut != -1) {
            ft.setCustomAnimations(animIn, animOut)
        }

        if (isAdd) {
            ft.add(contentFrame, addFragment, tag).addToBackStack(null)
        }
        else {
            ft.replace(contentFrame, addFragment, tag)
        }

        ft.commit()
    }


    /**
     * Dialog 생성 메소드

     * @param title   Dialog Title String Id
     * *
     * @param message Message String Id
     */
    protected fun showAlertDialog(title: Int, message: Int): Dialog {
        return this.showAlertDialog(title, message, -1, null, -1, null)
    }

    /**
     * Dialog 생성 메소드

     * @param title    Dialog Title String Id
     * *
     * @param message  Message String Id
     * *
     * @param buttonId Button String Id
     * *
     * @param listener Button Listener
     */
    protected fun showAlertDialog(title: Int, message: Int, buttonId: Int, listener: DialogInterface.OnClickListener): Dialog {
        return this.showAlertDialog(title, message, buttonId, listener, -1, null)
    }

    /**
     * Dialog 생성 메소드

     * @param title            Dialog Title String Id
     * *
     * @param message          Message String Id
     * *
     * @param positiveResId    Positive Button String Id
     * *
     * @param positiveListener Positive Button Listener
     * *
     * @param negativeResId    Negative Button String Id
     * *
     * @param negativeListener Negative Button Linstener
     */
    protected fun showAlertDialog(title: Int, message: Int, positiveResId: Int, positiveListener: DialogInterface.OnClickListener?,
                                  negativeResId: Int, negativeListener: DialogInterface.OnClickListener?): Dialog {
        val builder = AlertDialog.Builder(this)
        if (title != -1) {
            builder.setTitle(title)
        }
        if (message != -1) {
            builder.setMessage(message)
        }
        if (positiveResId != -1) {
            builder.setPositiveButton(positiveResId, positiveListener)
        }
        if (negativeResId != -1) {
            builder.setNegativeButton(negativeResId, negativeListener)
        }
        return builder.show()
    }

    protected fun showNetworkErrorDialog(error: Throwable) {
        AlertDialog.Builder(this).setMessage(error.message).show()
    }
}