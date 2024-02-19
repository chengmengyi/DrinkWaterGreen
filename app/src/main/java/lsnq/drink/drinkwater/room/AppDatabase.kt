package lsnq.drink.drinkwater.room

import androidx.room.Database
import androidx.room.RoomDatabase
import lsnq.drink.drinkwater.room.bean.RecordsBean
import lsnq.drink.drinkwater.room.bean.TodayDrinkBean
import lsnq.drink.drinkwater.room.dao.IRecordsDao

@Database(entities = [RecordsBean::class, TodayDrinkBean::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun iRecordDao(): IRecordsDao

}