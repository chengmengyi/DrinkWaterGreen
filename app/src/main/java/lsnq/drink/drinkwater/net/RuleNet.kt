package lsnq.drink.drinkwater.net

import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lsnq.drink.drinkwater.BuildConfig
import lsnq.drink.drinkwater.Value
import lsnq.drink.drinkwater.drinkLog
import lsnq.drink.drinkwater.urlencoding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RuleNet {

    fun getInfo() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val stringBuilder = StringBuilder(Value.RuleUrl)
                stringBuilder.apply {
                    append("?uracil=${NetConfig.getUuid().urlencoding()}")
                    append("&carthage=${System.currentTimeMillis().toString().urlencoding()}")
                    append("&chintz=${(Build.MODEL ?: "").urlencoding()}")
                    append("&connors=${BuildConfig.APPLICATION_ID.urlencoding()}")
                    append("&eagan=${(Build.VERSION.RELEASE ?: "").urlencoding()}")
                    append("&lose=${"".urlencoding()}")
                    append("&sparkle=${"".urlencoding()}")
                    append("&sodden=${NetConfig.androidId().urlencoding()}")
                    append("&shame=${"android".urlencoding()}")
                    append("&sumac=${"".urlencoding()}")
                    append("&frill=${BuildConfig.VERSION_NAME.urlencoding()}")
                    append("&pavilion=${"".urlencoding()}")
                    append("&h=${NetConfig.getIpAddress().urlencoding()}")
                    append("&buckeye=${"".urlencoding()}")
                }
                val request = Request.Builder().get().url(stringBuilder.toString()).build()
                NetConfig.drinkRetrofit().newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) = Unit
                    override fun onResponse(call: Call, response: Response)= Unit
                })
            }
        }
    }

}