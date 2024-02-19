package lsnq.drink.drinkwater.room.bean

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Keep
data class RecordsBean(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo val dataTime: String,
    @ColumnInfo val time: Long = 0,
    @ColumnInfo val drinkWaterSumValue: Int = 0,
    @ColumnInfo val drinkGoal: Int
)
