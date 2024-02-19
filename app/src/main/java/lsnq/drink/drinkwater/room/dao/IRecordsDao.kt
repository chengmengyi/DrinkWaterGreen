package lsnq.drink.drinkwater.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import lsnq.drink.drinkwater.room.bean.RecordsBean
import lsnq.drink.drinkwater.room.bean.TodayDrinkBean

@Dao
interface IRecordsDao {

    @Query("Select * from recordsBean where time > :time order by uid desc limit 7")
    fun getRecord(time: Long): MutableList<RecordsBean>

    @Insert
    fun addRecord(record: RecordsBean)

    @Query("Select * from recordsBean where dataTime = :date")
    fun getRecordFromDate(date: String): RecordsBean

    @Query("update recordsBean set drinkWaterSumValue = :drinkWaterSumValue ,drinkGoal =:drinkGoal where dataTime =:dataTime ")
    fun upDateRecord(drinkWaterSumValue: Int, drinkGoal: Int, dataTime: String)


    @Query("select * from TodayDrinkBean where drinkData= :drinkData")
    fun getTodayRecord(drinkData: String): MutableList<TodayDrinkBean>

    @Insert
    fun addTodayRecord(todayRecord: TodayDrinkBean)

    @Query("Select * from TodayDrinkBean where drinkData = :drinkData order by uid desc limit 1")
    fun getTodayRecordLast(drinkData: String): TodayDrinkBean

    @Query("delete from TodayDrinkBean where uid = :uid")
    fun deleteTodayRecord(uid: Int)
}