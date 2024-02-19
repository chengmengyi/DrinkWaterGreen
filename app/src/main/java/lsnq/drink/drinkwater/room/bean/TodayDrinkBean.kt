package lsnq.drink.drinkwater.room.bean

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Keep
data class TodayDrinkBean(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val drinkTime: String,
    @ColumnInfo val drinkWater: Int,
    @ColumnInfo val drinkData: String
)
