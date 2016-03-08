package chummong.io.catimagelist.presenter.main

import android.util.Log
import chummong.io.catimagelist.dto.FlickrResult
import chummong.io.catimagelist.http.RestApiManager
import chummong.io.catimagelist.http.service.PhotoService
import chummong.io.catimagelist.presenter.Persenter
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by LeeJongHun on 2016-03-07.
 */

interface MainPersenter : Persenter {
    fun getCatsList()
    fun moreGetCatsList()
}

class MainPersenterImpl(val view: MainView) : MainPersenter {
    private var MAX_IMAGES_PER_REQUEST = 10
    private var catsListSub: Subscription? = null
    private var moreCatsListSub: Subscription? = null

    override fun getCatsList() {
        view.showProgress()
        catsListSub = catsListRequest()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe { view.hiedProgress() }
                .subscribe(showCatList())
    }

    override fun moreGetCatsList() {
        if (moreCatsListSub != null) {
            return;
        }

        view.showProgress()
        Log.d("MainPersenterImpl", "moreGetCatsList")

        moreCatsListSub = moreCatsListRequest()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe { view.hiedProgress() }
                .subscribe(showMoreCatList())
    }

    private fun showCatList(): Subscriber<FlickrResult> {
        return object : Subscriber<FlickrResult>() {
            override fun onNext(result: FlickrResult) {
                view.showCatsList(result.photos.photos)
            }

            override fun onCompleted() {

            }

            override fun onError(error: Throwable) {
                throw error
            }
        }
    }

    private fun showMoreCatList(): Subscriber<FlickrResult> {
        return object : Subscriber<FlickrResult>() {
            override fun onNext(result: FlickrResult) {
                view.showCatsList(result.photos.photos)
            }

            override fun onCompleted() {
                moreCatsListSub = null
            }

            override fun onError(error: Throwable) {
                throw error
            }
        }
    }

    private fun catsListRequest(): Observable<FlickrResult> {
        MAX_IMAGES_PER_REQUEST = 10
        return RestApiManager.getApi(PhotoService::class.java).getCatList(MAX_IMAGES_PER_REQUEST)
    }

    private fun moreCatsListRequest(): Observable<FlickrResult> {
        MAX_IMAGES_PER_REQUEST += 10
        return RestApiManager.getApi(PhotoService::class.java).getCatList(MAX_IMAGES_PER_REQUEST)
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