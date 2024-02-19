package lsnq.drink.drinkwater.net

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.text.format.Formatter
import lsnq.drink.drinkwater.appContext
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.urlencoding
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import java.util.concurrent.TimeUnit

object NetConfig {

    private fun interceptor(): Interceptor {
        return Interceptor {
            val builder = it.request().newBuilder()
            builder.apply {
                kotlin.runCatching {
                    addHeader("magpie", (Build.MANUFACTURER ?: "").urlencoding())
                    addHeader("buckeye", "".urlencoding())
                    addHeader("carthage", System.currentTimeMillis().toString().urlencoding())
                }
            }
            it.proceed(builder.build())
        }
    }

    fun drinkRetrofit(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(interceptor())
                .readTimeout(10 * 1000, TimeUnit.SECONDS)
                .connectTimeout(10 * 1000, TimeUnit.SECONDS)
                .writeTimeout(10 * 1000, TimeUnit.SECONDS)
                .build()
    }

    @SuppressLint("HardwareIds")
    fun androidId(): String {
        runCatching {
            return Settings.Secure.getString(appContext.contentResolver, Settings.Secure.ANDROID_ID)
                ?: ""
        }
        return ""
    }

    fun getUuid(): String {
        runCatching {
            if (Mmkv.uuidDrink.isNotEmpty()) {
                return Mmkv.uuidDrink
            }
            val uuid = (UUID.randomUUID() ?: "").toString()
            if (uuid.isNotEmpty()) {
                Mmkv.uuidDrink = uuid
            }
            return uuid
        }
        return ""
    }

    fun getIpAddress(): String {
        runCatching {
            val wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            val ip = Formatter.formatIpAddress(ipAddress)
            if (ip.isNotEmpty()) {
                return ip
            }
        }
        runCatching {
            val networkInterfaces: Enumeration<NetworkInterface> =
                NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements()) {
                val networkInterface: NetworkInterface = networkInterfaces.nextElement()
                val inetAddresses: Enumeration<InetAddress> = networkInterface.inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    val inetAddress: InetAddress = inetAddresses.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress() ?: ""
                    }
                }
            }
        }
        return ""
    }

}