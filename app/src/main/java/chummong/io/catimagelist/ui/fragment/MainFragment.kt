package chummong.io.catimagelist.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import chummong.io.catimagelist.R
import chummong.io.catimagelist.dto.Photo
import chummong.io.catimagelist.presenter.main.MainPersenterImpl
import chummong.io.catimagelist.presenter.main.MainView
import chummong.io.catimagelist.ui.adapter.CatsAdapter
import org.jetbrains.anko.support.v4.find

/**
 * Created by LeeJongHun on 2016-03-07.
 */
class MainFragment : Fragment(), MainView {

    private val catsAdatper = CatsAdapter()

    private val persenter by lazy { MainPersenterImpl(this) }

    private val swipeLayout by lazy { find<SwipeRefreshLayout>(R.id.swipe_layout) }
    private val listView by lazy { find<RecyclerView>(R.id.list) }
    private val progress by lazy { find<ProgressBar>(R.id.progress) }
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

        listView.layoutManager = layoutManager
        listView.adapter = catsAdatper
        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount;
                val totalItemCount = layoutManager.itemCount;
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                Log.d("MainFragment", "visibleItemCount : ${visibleItemCount}, totalItemCount : ${totalItemCount}, " +
                        "firstVisibleItemPosition : ${firstVisibleItemPosition}")

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    persenter.moreGetCatsList()
                }
            }
        })

        swipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                                            android.R.color.holo_blue_light,
                                            android.R.color.holo_green_light)

        swipeLayout.setOnRefreshListener {
            catsAdatper.items = emptyList()
            persenter.getCatsList()
        }

        persenter.getCatsList()
    }

    override fun showCatsList(photos: List<Photo>) {
        catsAdatper.items = photos
    }

    override fun showMoreCatsList(photos: List<Photo>) {
        catsAdatper.items += photos
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hiedProgress() {
        swipeLayout.isRefreshing = false;
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        persenter.allThreadRemove()

        super.onDestroy()
    }
}

