package chummong.io.catimagelist.ui.activity

import android.os.Bundle
import android.util.Log
import chummong.io.catimagelist.R
import chummong.io.catimagelist.ui.fragment.MainFragment

class MainActivity : BaseActivity() {

    val mainFragment by lazy { MainFragment.getFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "Hello Kotlin Android App!");

        addFragment(R.id.main_frame, mainFragment, "MainFragment")
    }

}

