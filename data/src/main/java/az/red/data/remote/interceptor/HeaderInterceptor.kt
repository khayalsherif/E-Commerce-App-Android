package az.red.data.remote.interceptor

import az.red.data.local.SessionManager
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val headers = HashMap<String, String>()
        sessionManager.getAuthToken()?.let { headers["Authorization"] = it }

        val response = chain.proceed(request.headers(headers.toHeaders()).build())
        if (response.code == 401) {
            sessionManager.removeAuthToken()
            // Event bus logic
        }
        return response
    }
}