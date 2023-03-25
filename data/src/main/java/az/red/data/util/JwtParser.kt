package az.red.data.util

import android.os.Build
import org.json.JSONObject
import java.util.*

object JwtParser {

    fun getUserId(token: String): String {

        val mDecode = decodeToken(token)
        val test = JSONObject(mDecode)
        return test.getString("id")
    }

    private fun decodeToken(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val payload =
                String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            payload
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }
}