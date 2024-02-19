package lsnq.drink.drinkwater

import android.content.Context
import android.util.Log
import android.util.TypedValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lsnq.drink.drinkwater.mmkv.Mmkv
import lsnq.drink.drinkwater.room.bean.RecordsBean
import lsnq.drink.drinkwater.room.bean.TodayDrinkBean
import lsnq.drink.drinkwater.room.dao.IRecordsDao
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

val appContext: Context = DrinkApplication.application.applicationContext
val roomDao: IRecordsDao = DrinkApplication.roomDb.iRecordDao()

fun Float.dpToPx(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    appContext.resources.displayMetrics
).toInt()

fun String.drinkLog() = Log.d("Drink", this)

fun LifecycleOwner.roomRuning(run: () -> Unit) {
    this.lifecycleScope.launch(Dispatchers.IO) {
        run()
    }
}

fun LifecycleOwner.roomMainRuning(run: () -> Unit) {
    lifecycleScope.launch(Dispatchers.Main) {
        run()
    }
}

fun Boolean.goalValue(weight: Int): Int {
    val n: Float = if (this) {
        1f
    } else {
        0.9f
    }
    return (weight.toFloat() * 35f * n).toInt()
}

fun Int.lbsToKg(): Int {
    return (this * 0.45).toInt()
}

fun Boolean.isMale(): String {
    return if (this) {
        "Male"
    } else {
        "Female"
    }
}

fun String.weightShow(): Int {
    return if (this == "Kg") {
        Mmkv.weight
    } else {
        Mmkv.weightLbs
    }
}

fun Int.myrate(goal: Int): Int {
    if (this==0){
        return 0
    }
    val rate = this * 100 / goal
    return if (rate < 100) {
        if (rate < 1 && this > 0) {
            1
        } else {
            rate
        }
    } else {
        100
    }
}

fun String.timeForMD(): String {
    runCatching {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("$this 01:00:00")
        val timeStamp = date.time
        return SimpleDateFormat("MMMdd", Locale.US).format(timeStamp)
    }
    return ""
}

fun timeForYMD(): String {
    runCatching {
        val date = Date(System.currentTimeMillis())
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
    }
    return ""
}

fun Int.timeForYMD7(): Long {
    runCatching {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${timeForYMD()} 00:00:00")
        return date.time - this * 24 * 60 * 60 * 1000
    }
    return 0
}

fun Long.timeFMD(): String {
    runCatching {
        val date = Date(this)
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date)
    }
    return ""
}

fun timeForHHmm(): String {
    runCatching {
        val date = Date(System.currentTimeMillis())
        return SimpleDateFormat("HH:mm", Locale.US).format(date)
    }
    return ""
}

fun String.addOneHalfTime(): String {
    runCatching {
        var time: Long = 0
        val dateNow = System.currentTimeMillis()
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${timeForYMD()} $this:00")
        val date6 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${timeForYMD()} 06:00:00")
        val date22 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${timeForYMD()} 22:00:00")
        val timeStamp = date.time + 90 * 60 * 1000
        val timeStamp6 = date6.time
        val timeStamp22 = date22.time
        if (timeStamp < timeStamp6) {
            time = timeStamp6
        } else if (timeStamp > timeStamp22) {
            time = timeStamp22
        } else if (timeStamp<dateNow){
            time = dateNow
        }else{
            time = timeStamp
        }
        return SimpleDateFormat("HH:mm", Locale.US).format(time)
    }
    return ""
}

fun Int.addRecordDate() {
    val recordToday = roomDao.getRecordFromDate(timeForYMD())
    "recordToday = $recordToday  timeForYMD()=${timeForYMD()}".drinkLog()
    if (recordToday == null) {
        roomDao.addRecord(RecordsBean(0, timeForYMD(), System.currentTimeMillis(), this, Mmkv.goal))
    } else {
        roomDao.upDateRecord(this, Mmkv.goal, timeForYMD())
    }
}

fun getAvgDrinkDay(): String {
    val record = roomDao.getRecord(6.timeForYMD7())
    var sumWater = 0
    record.forEach {
        sumWater += it.drinkWaterSumValue
    }
    return "${sumWater / 7}ml/day"
}

fun getAvgDrinkTime(): String {
    val record = roomDao.getRecord(6.timeForYMD7())
    var time = 0
    record.forEach {
        time += roomDao.getTodayRecord(it.dataTime).size
    }
    return "${"%.1f".format(time.toDouble() / 7f)}times/day"
}

fun getAvgRate(): String {
    val record = roomDao.getRecord(6.timeForYMD7())
    var finishRate = 0
    record.forEach {
        finishRate += it.drinkWaterSumValue.myrate(it.drinkGoal)
    }
    val finishRate1 = finishRate / 7
    return if (finishRate1<1){
        return "1%"
    }else{
        "$finishRate1%"
    }
}

fun Int.deleteRecordDate() {
    val recordToday = roomDao.getRecordFromDate(timeForYMD())
    val sum = recordToday.drinkWaterSumValue - this
    roomDao.upDateRecord(sum, Mmkv.goal, timeForYMD())
}

fun Int.addRecordToday() {
    roomDao.addTodayRecord(TodayDrinkBean(0, timeForHHmm(), this, timeForYMD()))
}

fun getDrinkValue(): Int {
    val record = roomDao.getRecordFromDate(timeForYMD())
    return if (record == null) {
        0
    } else {
        record.drinkWaterSumValue
    }
}

fun getTodayRecordLast(): String {
    val recordToday = roomDao.getTodayRecordLast(timeForYMD())
    return if (recordToday == null) {
        timeForHHmm()
    } else {
        recordToday.drinkTime.addOneHalfTime()
    }
}

fun String?.urlencoding(): String {
    if (this.isNullOrBlank()) return ""
    return URLEncoder.encode(this, "UTF-8")
}