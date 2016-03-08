package chummong.io.catimagelist.http

import chummong.io.catimagelist.App
import chummong.io.catimagelist.common.Const
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by LeeJongHun on 2016-03-07.
 */

object RestApiManager {
    private var retrofit: Retrofit? = null

    fun <S> getApi(service: Class<S>): S {

        if (retrofit == null) {
            synchronized(RestApiManager::class.java) {
                if (retrofit == null) {
                    initRetrofit()
                }
            }
        }

        return retrofit!!.create<S>(service)
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder().baseUrl(Const.END_PONIT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initOkhttpClient())
                .build()
    }

    private fun initOkhttpClient(): OkHttpClient? {
        val cacheSize = 10 * 1024 * 1024 // 10MB
        val cachePath = App.CONTEXT?.cacheDir

        return OkHttpClient.Builder().cache(Cache(cachePath, cacheSize.toLong()))
                .addInterceptor(RequestInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build()
    }
}

private class RequestInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response? {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("api_key", Const.API_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1").build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }
}