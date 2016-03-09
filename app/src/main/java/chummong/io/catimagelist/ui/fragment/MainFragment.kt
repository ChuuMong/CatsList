package chummong.io.catimagelist.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import chummong.io.catimagelist.R
import chummong.io.catimagelist.model.dto.Photo
import chummong.io.catimagelist.presenter.main.MainPersenterImpl
import chummong.io.catimagelist.presenter.main.MainView
import chummong.io.catimagelist.ui.adapter.CatsAdapter
import org.jetbrains.anko.support.v4.find

/**
 * Created by LeeJongHun on 2016-03-07.
 */
class MainFragment : BaseFragment(), MainView {

    private val catsAdatper = CatsAdapter()

    private val persenter by lazy { MainPersenterImpl(this) }

    private val swipeLayout by lazy { find<SwipeRefreshLayout>(R.id.swipe_layout) }
    private val listView by lazy { find<RecyclerView>(R.id.list) }
    //    private val progress by lazy { find<ProgressBar>(R.id.progress) }
    private val layoutManager by lazy { GridLayoutManager(activity, 2) }

    companion object {
        fun getFragment(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Grid Span
        layoutManager.spanSizeLookup = spanSizeLookup
        listView.layoutManager = layoutManager
        listView.adapter = catsAdatper
        listView.addOnScrollListener(scrollListener)

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                                            android.R.color.holo_blue_light,
                                            android.R.color.holo_green_light)
        swipeLayout.setOnRefreshListener(refreshListener)
        persenter.getCatsList()
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

    override fun finish() {

    }

    override fun networkError(e: Throwable) {
        showNetworkErrorDialog(e.message!!)
    }

    override fun showNetworkErrorDialog(msg: String) {
        AlertDialog.Builder(activity).setMessage(msg).show()
    }

    override fun onDestroy() {
        persenter.allThreadRemove()

        super.onDestroy()
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
                persenter.moreGetCatsList()
            }
        }
    }

    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        catsAdatper.items = emptyList()
        persenter.getCatsList()
        catsAdatper.footer?.hideProgoress()
    }
}

