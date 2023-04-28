package com.hebs.moviedb.data.remote.common

import java.util.Locale
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LanguageInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter("language", Locale.getDefault().toLanguageTag())
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}