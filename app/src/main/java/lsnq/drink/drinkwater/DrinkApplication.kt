package lsnq.drink.drinkwater

import android.app.Application
import androidx.room.Room
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.tencent.mmkv.MMKV
import lsnq.drink.drinkwater.net.RuleNet
import lsnq.drink.drinkwater.room.AppDatabase

class DrinkApplication : Application() {

    private val databaseName = "Record_DB"

    override fun onCreate() {
        super.onCreate()
        application = this
        roomDb = Room.databaseBuilder(this, AppDatabase::class.java, databaseName).build()
        registerActivityLifecycleCallbacks(LifecycleDrink())
        MMKV.initialize(this)
        RuleNet().getInfo()
        Firebase.initialize(this)
    }

    companion object {
        lateinit var application: Application
        lateinit var roomDb: AppDatabase
    }
}