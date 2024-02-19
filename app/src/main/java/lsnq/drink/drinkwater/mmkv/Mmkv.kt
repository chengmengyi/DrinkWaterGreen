package lsnq.drink.drinkwater.mmkv

import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Mmkv {

    var isPlanFinish by MmkvInit("isPlanFinish", false)
    var isMale by MmkvInit("isMale", true)
    var weight by MmkvInit("weight", 50)
    var weightLbs by MmkvInit("weightLbs", 50)
    var unit by MmkvInit("unit", "Kg")
    var goal by MmkvInit("goal", 2000)
    var uuidDrink by MmkvInit("uuidDrink","")


    private val m = MMKV.defaultMMKV()

    fun <T> encode(key: String, value: T) {
        when (value) {
            is Int -> m.encode(key, value)
            is Boolean -> m.encode(key, value)
            is String -> m.encode(key, value)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> decode(key: String, defValue: T): T {
        return when (defValue) {
            is Int -> m.decodeInt(key, defValue) as T
            is Boolean -> m.decodeBool(key, defValue) as T
            is String -> m.decodeString(key, defValue) as T
            else -> defValue
        }
    }

    class MmkvInit<T>(private val key: String, private val def: T) : ReadWriteProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T = decode(key, def)
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = encode(key, value)
    }

}