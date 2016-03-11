package chummong.io.catimagelist.presenter.main

import chummong.io.catimagelist.http.RestApiManager
import chummong.io.catimagelist.http.service.PhotoService
import chummong.io.catimagelist.model.dto.Photo
import chummong.io.catimagelist.model.dto.PhotoSearch
import chummong.io.catimagelist.presenter.Persenter
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.lang.kotlin.subscriber
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by LeeJongHun on 2016-03-07.
 */

interface MainPersenter : Persenter {
    fun getCatsList()
    fun moreGetCatsList()
}

class MainPersenterImpl
@Inject constructor(val view: MainView) : MainPersenter {
    private var MAX_IMAGES_PER_REQUEST = 1
    private var catsListSub: Subscription? = null
    private var moreCatsListSub: Subscription? = null

    override fun getCatsList() {
        view.showProgress()
        MAX_IMAGES_PER_REQUEST = 1

        catsListSub = catsListRequest(MAX_IMAGES_PER_REQUEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe { view.hideProgress() }
                .map(makeUniqueltIdList())
                .subscribe(subscriber<List<Photo>>()
                                   .onNext { result -> view.showCatsList(result) }
                                   .onError { error -> view.networkError(error) })
    }

    override fun moreGetCatsList() {
        if (moreCatsListSub != null && !moreCatsListSub!!.isUnsubscribed) {
            return
        }

        view.showProgress()

        moreCatsListSub = catsListRequest(++MAX_IMAGES_PER_REQUEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe { view.hideProgress() }
                .map(makeUniqueltIdList())
                .subscribe(subscriber<List<Photo>>()
                                   .onNext { result -> view.showMoreCatsList(result) }
                                   .onError { error -> view.networkError(error) })
    }

    private fun makeUniqueltIdList(): Func1<PhotoSearch, ArrayList<Photo>> {
        return Func1 { result ->
            var tempList: ArrayList<Photo> = ArrayList()
            var isNotCat = false

            for (item in result.photos) {
                if (!tempList.contains(item)) {
                    for (tag in item.tags) {
                        if (tag.toLowerCase().contains("lion") || tag.toLowerCase().contains("tiger") ||
                                tag.toLowerCase().contains("puma") || tag.toLowerCase().contains("leopard") ||
                                tag.toLowerCase().contains("safari") || tag.toLowerCase().contains("africa")) {
                            isNotCat = true
                        }
                    }

                    if (!isNotCat) {
                        tempList.add(item)
                    }

                    isNotCat = false
                }
            }

            tempList
        }
    }

    private fun catsListRequest(page: Int): Observable<PhotoSearch> {
        return RestApiManager.getApi(PhotoService::class.java).getPhotoList(page)
    }

    override fun allThreadRemove() {
        if (catsListSub != null && !catsListSub!!.isUnsubscribed) {
            catsListSub?.unsubscribe()
        }

        if (moreCatsListSub != null && !moreCatsListSub!!.isUnsubscribed) {
            moreCatsListSub?.unsubscribe()
        }
    }
}