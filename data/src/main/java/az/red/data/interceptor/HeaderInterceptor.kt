package az.red.data.interceptor

import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val headers = HashMap<String, String>()
        val response = chain.proceed(request.headers(headers.toHeaders()).build())

        if (response.code == 401) {
            // Refresh Token
        }
        return response
    }
}