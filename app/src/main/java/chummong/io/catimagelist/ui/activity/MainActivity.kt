package chummong.io.catimagelist.ui.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import butterknife.Bind
import butterknife.ButterKnife
import chummong.io.catimagelist.R
import chummong.io.catimagelist.model.dto.Photo
import chummong.io.catimagelist.presenter.main.MainPersenter
import chummong.io.catimagelist.presenter.main.MainView
import chummong.io.catimagelist.presenter.main.di.DaggerMainComponent
import chummong.io.catimagelist.presenter.main.di.MainModule
import chummong.io.catimagelist.ui.adapter.CatsAdapter
import chummong.io.catimagelist.ui.adapter.OnClickListener
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, OnClickListener {

    lateinit private var layoutManager: GridLayoutManager
    private val catsAdatper = CatsAdapter(this)

    @Bind(R.id.swipe_layout)
    lateinit var swipeLayout: SwipeRefreshLayout
    @Bind(R.id.list)
    lateinit var listView: RecyclerView

    @Inject
    lateinit var mainPersenter: MainPersenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        DaggerMainComponent.builder().mainModule(MainModule(this)).build().inject(this)

        Log.d("MainActivity", "Hello Kotlin Android App!");

        layoutManager = GridLayoutManager(this, 2)
        layoutManager.spanSizeLookup = spanSizeLookup
        listView.layoutManager = layoutManager
        listView.adapter = catsAdatper
        listView.addOnScrollListener(scrollListener)

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                                            android.R.color.holo_blue_light,
                                            android.R.color.holo_green_light)
        swipeLayout.setOnRefreshListener(refreshListener)
        mainPersenter.getCatsList()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        mainPersenter.allThreadRemove()

        super.onDestroy()
    }

    override fun showCatsList(photos: List<Photo>) {
        catsAdatper.items = photos
    }

    override fun showMoreCatsList(photos: List<Photo>) {
        catsAdatper.items += photos
    }

    override fun showProgress() {
        catsAdatper.footer?.showProgress()
    }

    override fun hideProgress() {
        catsAdatper.footer?.hideProgoress()
        swipeLayout.isRefreshing = false;
    }

    override fun networkError(e: Throwable) {
        showNetworkErrorDialog(e)
    }

    override fun onClick(view: View, position: Int) {

    }

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            if (catsAdatper.isFooter(position)) {
                return layoutManager.spanCount
            }
            else {
                return 1
            }
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = layoutManager.childCount;
            val totalItemCount = layoutManager.itemCount;
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                Log.d("MainFragment",
                      "visibleItemCount : ${visibleItemCount}, totalItemCount : ${totalItemCount}, "
                              + "firstVisibleItemPosition : ${firstVisibleItemPosition}")
                mainPersenter.moreGetCatsList()
            }
        }
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        catsAdatper.items = emptyList()
        mainPersenter.getCatsList()
        catsAdatper.footer?.hideProgoress()
    }
}

